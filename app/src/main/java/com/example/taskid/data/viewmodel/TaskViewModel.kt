package com.example.taskid.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskid.data.TaskIDDatabase
import com.example.taskid.data.models.TaskData
import com.example.taskid.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel (application: Application):AndroidViewModel(application) {
    private val taskDao= TaskIDDatabase.getDatabase(application).taskDAO()
    private val repository:TaskRepository = TaskRepository(taskDao)

    val getData:LiveData<List<TaskData>> = repository.getData

    fun insertData(taskData: TaskData){
        viewModelScope.launch (Dispatchers.IO){
            repository.insertData(taskData)
        }
    }

    fun updateData(taskData: TaskData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(taskData)
        }
    }
}