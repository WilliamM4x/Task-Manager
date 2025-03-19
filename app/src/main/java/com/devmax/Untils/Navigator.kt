package com.devmax.Untils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity


@Suppress("DEPRECATION")
class Navigator {
    companion object{
        fun goTo(context: Context, activitygo: Class<*>){
            val intnt = Intent(context,activitygo)
           startActivity(context,intnt, null)
          //  finish()
        }
    }
}