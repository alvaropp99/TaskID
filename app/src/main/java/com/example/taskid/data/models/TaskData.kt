package com.example.taskid.data.models

import android.os.Parcelable
import androidx.room.Entity;
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// TODO BIEN EN ESTA CLASE

@Entity(tableName = "task_table")
@Parcelize
data class TaskData (

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority: Priority,
    var description:String
): Parcelable