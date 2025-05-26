package com.example.s04_quizz

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.InputStream

class QuizActivity : AppCompatActivity() {
    private lateinit var questionList: List<Question>
    private var currentIndex = 0
    private var score = 0
    private lateinit var selectedButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val username = intent.getStringExtra("username")
        findViewById<TextView>(R.id.textUserName).text = "Hola, $username!"

        questionList = loadQuestionsFromAssets()
        selectedButtons = listOf(
            findViewById(R.id.buttonOption1),
            findViewById(R.id.buttonOption2),
            findViewById(R.id.buttonOption3),
            findViewById(R.id.buttonOption4)
        )

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
        if (currentIndex >= questionList.size) {
            findViewById<TextView>(R.id.textQuestion).text = "¡Quiz terminado! Puntaje: $score"
            selectedButtons.forEach { it.isEnabled = false }
            return
        }

        val q = questionList[currentIndex]
        findViewById<TextView>(R.id.textQuestion).text = q.questionText

        selectedButtons.forEachIndexed { index, button ->
            button.setBackgroundColor(Color.LTGRAY)
            button.text = q.options[index]
            button.isEnabled = true
            button.setOnClickListener {
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
    }
}
