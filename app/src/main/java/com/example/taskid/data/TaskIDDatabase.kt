package com.example.taskid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskid.data.models.TaskData

@Database(entities = [TaskData::class], version = 1, exportSchema = false)
@TypeConverters (Converter::class)
abstract class TaskIDDatabase : RoomDatabase(){

    abstract fun taskDAO():TaskDAO

    companion object{

        @Volatile
        private var INSTANCE :TaskIDDatabase?=null

        fun getDatabase(context: Context):TaskIDDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskIDDatabase::class.java,
                        "taskID_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}