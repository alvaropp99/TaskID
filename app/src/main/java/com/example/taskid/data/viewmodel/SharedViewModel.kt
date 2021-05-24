package com.example.taskid.data.viewmodel

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.taskid.R


// Se crea la clase SharedViewModel para reutilizar aquellos métodos
// los cuales su uso va a ser utilizado en varias clases
class SharedViewModel (application: Application):AndroidViewModel(application) {

    // Este listener va a establecer que los tipos de prioridades
    // tengan un color en función de la prioridad que tenga
    val listener:AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{

        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(
                parent: AdapterView<*>?
                , view: View?
                , position: Int
                , id: Long
        ) {
            when(position){
                0 -> { (parent?.getChildAt(0)as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> { (parent?.getChildAt(0)as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> { (parent?.getChildAt(0)as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }
    }


}