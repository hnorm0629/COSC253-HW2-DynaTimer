package com.example.cosc253_hw2_dynatimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // remove drop shadow below action bar
        supportActionBar!!.elevation = 0f
    }
}