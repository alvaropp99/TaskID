package com.example.taskid.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskid.data.models.TaskData

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun getData():LiveData<List<TaskData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData (taskData : TaskData)

    @Update
    suspend fun updateData(taskData: TaskData)
}