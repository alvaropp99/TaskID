package com.example.taskid.data.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.taskid.data.TaskDAO
import com.example.taskid.data.models.TaskData
import java.lang.StringBuilder

class TaskRepository (private val taskDAO: TaskDAO) {
    val getData: LiveData<List<TaskData>> = taskDAO.getData()
    val sortByHigh: LiveData<List<TaskData>> = taskDAO.sortByHigh()
    val sortByLow: LiveData<List<TaskData>> = taskDAO.sortByLow()

    suspend fun insertData(taskData: TaskData){
        taskDAO.insertData(taskData)
    }

    suspend fun updateData(taskData: TaskData){
        taskDAO.updateData(taskData)
    }

    suspend fun deleteData (taskData: TaskData){
        taskDAO.deleteData(taskData)
    }

    suspend fun deleteAllTasks(){
        taskDAO.deleteAllTasks()
    }

    fun searchDb(searchQuery: String): LiveData<List<TaskData>>{
        return taskDAO.searchDb(searchQuery)
    }
}