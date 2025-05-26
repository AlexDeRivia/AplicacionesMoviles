package com.example.s05_clinicarobles


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnServicios = findViewById<Button>(R.id.btnVerServicios)
        btnServicios.setOnClickListener {
            startActivity(Intent(this, SpecialtiesActivity::class.java))
        }
    }
}



