package com.example.taskid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)

        setTheme(R.style.Theme_TaskID)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.navFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController=findNavController(R.id.navFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}