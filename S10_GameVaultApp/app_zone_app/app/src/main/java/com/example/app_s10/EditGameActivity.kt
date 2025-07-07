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


class EditGameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var etTitle: TextInputEditText
    private lateinit var etGenre: TextInputEditText
    private lateinit var ratingBar: RatingBar
    private lateinit var btnSave: Button

    private var gameId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_game)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        etTitle = findViewById(R.id.etGameTitle)
        etGenre = findViewById(R.id.etGameGenre)
        ratingBar = findViewById(R.id.ratingBar)
        btnSave = findViewById(R.id.btnSaveGame)

        // Obtener datos del intent
        gameId = intent.getStringExtra("gameId")
        val title = intent.getStringExtra("title")
        val genre = intent.getStringExtra("genre")
        val rating = intent.getFloatExtra("rating", 0f)

        etTitle.setText(title)
        etGenre.setText(genre)
        ratingBar.rating = rating

        btnSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {
        val userId = auth.currentUser?.uid ?: return
        val gameId = gameId ?: return

        val updatedGame = Game(
            id = gameId,
            title = etTitle.text.toString(),
            genre = etGenre.text.toString(),
            rating = ratingBar.rating,
            userId = userId
        )

        // Guardar en la ruta correcta: games/userId/gameId
        database.child("games").child(userId).child(gameId).setValue(updatedGame)
            .addOnSuccessListener {
                Toast.makeText(this, "Juego actualizado", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
    }

}
