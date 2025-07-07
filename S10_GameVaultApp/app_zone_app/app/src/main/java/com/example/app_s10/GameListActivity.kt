package com.example.app_s10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.text.Editable
import android.text.TextWatcher


class GamesListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameAdapter
    private lateinit var spinnerGenre: Spinner
    private lateinit var etSearchTitle: EditText


    private val gameList = mutableListOf<Game>()
    private var allGames: List<Game> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
        etSearchTitle = findViewById(R.id.etSearchTitle)


        spinnerGenre = findViewById(R.id.spinnerGenre)
        recyclerView = findViewById(R.id.recyclerViewGames)

        // Adaptador con opciones de editar y eliminar
        adapter = GameAdapter(
            games = gameList,
            onEditClick = { gameToEdit ->
                val intent = Intent(this, EditGameActivity::class.java)
                intent.putExtra("gameId", gameToEdit.id)
                intent.putExtra("title", gameToEdit.title)
                intent.putExtra("genre", gameToEdit.genre)
                intent.putExtra("rating", gameToEdit.rating)
                startActivity(intent)
            },
            onDeleteClick = { gameToDelete ->
                showDeleteConfirmationDialog(gameToDelete)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        loadGamesFromFirebase()
    }

    private fun setupGenreFilterDinamico(juegos: List<Game>) {
        val generosUnicos = juegos.map { it.genre.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
            .toMutableList()
            .sorted()

        val genres = mutableListOf("Todos")
        genres.addAll(generosUnicos)

        val adapterSpinner = ArrayAdapter(this, R.layout.custom_spinner_item, genres)
        adapterSpinner.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)


        spinnerGenre.adapter = adapterSpinner

        spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGenre = parent.getItemAtPosition(position).toString()
                applyCombinedFilters()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        etSearchTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyCombinedFilters()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }


    private fun loadGamesFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userGamesRef = FirebaseDatabase.getInstance()
            .getReference("games")
            .child(userId)

        userGamesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allGames = snapshot.children.mapNotNull { it.getValue(Game::class.java) }
                setupGenreFilterDinamico(allGames)  // ← Llama aquí con la data real
                applyCombinedFilters()


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GamesListActivity, "Error al cargar juegos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun applyCombinedFilters() {
        val genre = spinnerGenre.selectedItem.toString()
        val query = etSearchTitle.text.toString().trim()

        val filtered = allGames.filter { game ->
            val matchesGenre = (genre == "Todos" || game.genre.equals(genre, ignoreCase = true))
            val matchesTitle = game.title.contains(query, ignoreCase = true)
            matchesGenre && matchesTitle
        }

        gameList.clear()
        gameList.addAll(filtered)
        adapter.notifyDataSetChanged()
    }


    private fun showDeleteConfirmationDialog(game: Game) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar juego")
            .setMessage("¿Estás seguro de eliminar '${game.title}'?")
            .setPositiveButton("Eliminar") { _, _ -> deleteGame(game) }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteGame(game: Game) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameRef = FirebaseDatabase.getInstance()
            .getReference("games")
            .child(userId)
            .child(game.id)

        gameRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Juego eliminado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
            }
    }
}
