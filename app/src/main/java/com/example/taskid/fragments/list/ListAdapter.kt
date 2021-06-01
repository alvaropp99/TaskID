package com.example.taskid.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskid.R
import com.example.taskid.data.models.TaskData
import com.example.taskid.databinding.FragmentAddBinding
import com.example.taskid.databinding.TaskLayoutBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder> (){

    var dataList = emptyList<TaskData>()
    class MyViewHolder(private val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(taskData: TaskData){
            binding.taskData = taskData
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskLayoutBinding.inflate(layoutInflater, parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Al haber hecho previamente el binding, mediante el parent se está referenciando
        // el inflated layout por lo cual recibiría todos los elementos del layout
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // La llamada a los campos del layout se haría mediante un binding dentro del propio layout
        val currentItem = dataList[position]
        holder.bind(currentItem)

    }

    // Devuelve el número total de datos que hay dentro del ListAdapter
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(taskData: List<TaskData>){
        this.dataList = taskData
        notifyDataSetChanged()
    }
}