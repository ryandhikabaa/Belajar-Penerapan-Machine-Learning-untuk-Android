package com.dicoding.asclepius.view.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.asclepius.databinding.ActivityDashboardBinding
import com.dicoding.asclepius.view.MainActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btDetectionHere.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                startActivity(intent)
            })
        }
    }
}