package com.example.taskid.data.repository

import androidx.lifecycle.LiveData
import com.example.taskid.data.TaskDAO
import com.example.taskid.data.models.TaskData

class TaskRepository (private val taskDAO: TaskDAO) {
    val getData: LiveData<List<TaskData>> = taskDAO.getData()

    suspend fun insertData(taskData: TaskData){
        taskDAO.insertData(taskData)
    }

    suspend fun updateData(taskData: TaskData){
        taskDAO.updateData(taskData)
    }
}