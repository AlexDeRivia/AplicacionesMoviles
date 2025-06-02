package com.example.s04_quizz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.json.JSONArray
import java.io.InputStream

class QuizActivity : AppCompatActivity() {
    private lateinit var questionList: List<Question>
    private var currentIndex = 0
    private var score = 0
    private lateinit var selectedButtons: List<Button>
    private var username: String? = null

    private lateinit var progressTimer: CircularProgressIndicator
    private var countDownTimer: CountDownTimer? = null
    private val totalTime = 10000L  // 10 segundos
    private val interval = 50L      // cada 50ms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        username = intent.getStringExtra("username")


        questionList = loadQuestionsFromAssets()

        selectedButtons = listOf(
            findViewById(R.id.buttonOption1),
            findViewById(R.id.buttonOption2),
            findViewById(R.id.buttonOption3),
            findViewById(R.id.buttonOption4)
        )

        progressTimer = findViewById(R.id.progressTimer)
        progressTimer.max = totalTime.toInt()

        loadQuestion()
    }

    private fun loadQuestionsFromAssets(): List<Question> {
        val json: String
        val inputStream: InputStream = assets.open("QuestionBank.json")
        json = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(json)
        val questions = mutableListOf<Question>()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val id = item.getInt("id")
            val text = item.getString("questionText")
            val optionsArray = item.getJSONArray("options")
            val options = mutableListOf<String>()
            for (j in 0 until optionsArray.length()) {
                options.add(optionsArray.getString(j))
            }
            val correctIndex = item.getInt("correctAnswerIndex")
            questions.add(Question(id, text, options, correctIndex))
        }
        return questions
    }

    private fun loadQuestion() {
        // Cancelar timer previo si existe
        countDownTimer?.cancel()

        if (currentIndex >= questionList.size) {
            // Ir a la pantalla de resultado
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
            return
        }

        val q = questionList[currentIndex]

        val questionTextView = findViewById<TextView>(R.id.textQuestion)
        questionTextView.text = q.questionText
        questionTextView.alpha = 0f
        questionTextView.animate().alpha(1f).setDuration(500).start()

        selectedButtons.forEachIndexed { index, button ->
            button.setBackgroundColor(Color.LTGRAY)
            button.text = q.options[index]
            button.isEnabled = true
            button.setOnClickListener {
                countDownTimer?.cancel()  // Cancela timer cuando responden
                if (index == q.correctAnswerIndex) {
                    button.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    button.setBackgroundColor(Color.RED)
                    selectedButtons[q.correctAnswerIndex].setBackgroundColor(Color.GREEN)
                    score--
                }
                selectedButtons.forEach { it.isEnabled = false }
                button.postDelayed({
                    currentIndex++
                    loadQuestion()
                }, 1000)
            }
        }

        startTimer()
    }

    private fun startTimer() {
        progressTimer.progress = 0

        countDownTimer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (totalTime - millisUntilFinished).toInt()
                progressTimer.progress = progress
            }

            override fun onFinish() {
                progressTimer.progress = totalTime.toInt()
                selectedButtons.forEach { it.isEnabled = false }
                val q = questionList[currentIndex]
                selectedButtons[q.correctAnswerIndex].setBackgroundColor(Color.GREEN)
                progressTimer.postDelayed({
                    currentIndex++
                    loadQuestion()
                }, 1000)
            }
        }.start()
    }
}
