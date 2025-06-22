        package com.example.s04_quizz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userName = intent.getStringExtra("username") ?: "Usuario"
        val score = intent.getIntExtra("score", 0)

        // Guardar en SharedPreferences
        val prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("lastUser", userName)
        editor.putInt("lastScore", score)
        editor.apply()

        val textViewResult = findViewById<TextView>(R.id.textResultScore)
        val buttonRestart = findViewById<Button>(R.id.buttonRestart)
        val buttonHome = findViewById<Button>(R.id.buttonReturnHome)

        textViewResult.text = "¡$userName, tu puntaje es $score!"

        buttonRestart.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("username", userName)
            startActivity(intent)
            finish()
        }

        buttonHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
