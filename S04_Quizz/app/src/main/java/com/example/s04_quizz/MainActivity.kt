package com.example.s04_quizz


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.editTextName)
        val startButton = findViewById<Button>(R.id.buttonStart)



        startButton.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("username", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}