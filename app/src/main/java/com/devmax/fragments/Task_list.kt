package com.devmax.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
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

        task.setOnItemClickListener { parent, view, position, id ->
            val selectedTask = data[position]

            Navigator.goTo(view.context, Task_edit::class.java)
        }

        loadTask(adapter)

        return view
    }

    private fun loadTask(adapter: ArrayAdapter<String>) {
        dataBaseRef.addValueEventListener(object : ValueEventListener {
            val auxAdapter=adapter
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for(child in snapshot.children){
                    val task = child.child("task").getValue(String::class.java)
                    val description = child.child("description").getValue(String::class.java)
                    val date = child.child("date").getValue(String::class.java)
                    val time = child.child("time").getValue(String::class.java)

                    if (task != null && description != null && date != null && time != null) {
                        data.add("$task\n$description\n$date\n$time")
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(requireContext(), R.string.loadTaskFail, Toast.LENGTH_SHORT).show()
            }

        })
    }


}