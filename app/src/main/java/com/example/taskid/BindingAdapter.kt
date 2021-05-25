package com.example.taskid

import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.findNavController
import com.example.taskid.data.models.Priority
import com.example.taskid.data.models.TaskData
import com.example.taskid.fragments.list.ListFragmentDirections

class BindingAdapter {

    companion object{

        @BindingAdapter("android.parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))}
                Priority.MEDIUM -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))}
                Priority.LOW -> {cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))}
            }
        }
    }
}