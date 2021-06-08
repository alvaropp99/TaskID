package com.example.taskid.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val sortByHigh: LiveData<List<TaskData>> = repository.sortByHigh
    val sortByLow: LiveData<List<TaskData>> = repository.sortByLow

    val emptyDb: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDbEmpty(taskData: List <TaskData>){
        emptyDb.value = taskData.isEmpty()
    }

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

    fun deleteData(taskData: TaskData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(taskData)
        }
    }

    fun deleteAllTasks(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTasks()
        }
    }
}