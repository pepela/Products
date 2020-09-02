package com.peranidze.products.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.peranidze.products.R
import com.peranidze.products.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}
