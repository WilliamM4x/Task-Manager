package com.devmax.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.devmax.Untils.Navigator
import com.devmax.profile.R
import com.devmax.profile.Task_edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Task_list : Fragment() {



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

        return view
    }




}