package com.example.app_s10

import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


// app/src/main/java/com/example/app_s10/AddGameActivity.kt
class AddGameActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        setupViews()
    }

    private fun setupViews() {
        val etTitle = findViewById<TextInputEditText>(R.id.etGameTitle)
        val etGenre = findViewById<TextInputEditText>(R.id.etGameGenre)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val btnSave = findViewById<Button>(R.id.btnSaveGame)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val genre = etGenre.text.toString().trim()
            val rating = ratingBar.rating

            if (validateInput(title, genre)) {
                saveGame(title, genre, rating)
            }
        }
    }

    private fun validateInput(title: String, genre: String): Boolean {
        if (title.isEmpty()) {
            showError("El título es obligatorio")
            return false
        }
        if (genre.isEmpty()) {
            showError("El género es obligatorio")
            return false
        }
        return true
    }

    private fun saveGame(title: String, genre: String, rating: Float) {
        val userId = auth.currentUser?.uid ?: return
        val gameRef = database.child("games").child(userId).push()
        val gameId = gameRef.key ?: return

        val game = Game(
            id = gameId,
            title = title,
            genre = genre,
            rating = rating,
            userId = userId
        )

        database.child("games").child(userId).child(gameId).setValue(game)
            .addOnSuccessListener {
                showSuccess("¡Juego guardado exitosamente!")
                finish()
            }
            .addOnFailureListener { exception ->
                showError("Error al guardar: ${exception.message}")
            }
    }



    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}