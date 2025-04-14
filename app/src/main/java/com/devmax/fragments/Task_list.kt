package com.devmax.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.devmax.Untils.Navigator
import com.devmax.profile.R
import com.devmax.profile.Task_edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Task_list : Fragment() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser?.uid
    val dataBaseRef = FirebaseDatabase.getInstance().getReference("users/$uid/tasks")
    var data = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_task_list, container, false)

        val task = view.findViewById<ListView>(R.id.listTask)

        val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, data)
        task.adapter = adapter

        loadTask(task,adapter)

        return view
    }

    private fun loadTask(task: ListView,adapter: ArrayAdapter<String>) {

        dataBaseRef.addValueEventListener(object : ValueEventListener {
            val auxAdapter=adapter

            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()

                for (child in snapshot.children) {
                      data.add(child.child("title").value.toString())

                }

                adapter.notifyDataSetChanged()

                task.setOnItemClickListener { parent, view, position, id ->
                    val selectedTask = snapshot.children.toList()[position].key

                    val intent= Intent(view.context, Task_edit::class.java)
                    intent.putExtra("selectedTask", selectedTask)
//                    val intentDT= Intent(view.context, PickDate::class.java)
//                    intentDT.putExtra("selectedTask", selectedTask)

                    Navigator.goTo(view.context, Task_edit::class.java)
                }

                task.setOnItemLongClickListener { parent, view,position, id ->
                    val selectedTask = snapshot.children.toList()[position].key

                    if (selectedTask != null) {
                        AlertDialog.Builder(view.context)
                            .setTitle(R.string.deleteTask)
                            .setMessage(R.string.mensegeDeleteTask)
                            .setPositiveButton(R.string.confirmDeleteTask){ dialog, which ->
                                dataBaseRef.child(selectedTask).removeValue()
                                dialog.dismiss()
                                Toast.makeText(view.context, R.string.sucessDeleteTask, Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton(R.string.cancel){dialog, wich ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                    true

                }

            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(requireContext(), R.string.loadTaskFail, Toast.LENGTH_SHORT).show()
            }

        })
    }


}