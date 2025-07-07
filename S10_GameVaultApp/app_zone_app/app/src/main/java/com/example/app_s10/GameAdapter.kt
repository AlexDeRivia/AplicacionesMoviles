package com.example.app_s10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextThemeWrapper

class GameAdapter(
    private var games: List<Game>,
    private val onEditClick: (Game) -> Unit,
    private val onDeleteClick: (Game) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
        val tvGenre: TextView = itemView.findViewById(R.id.tvGameGenre)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarItem)
        val btnOptions: ImageButton = itemView.findViewById(R.id.btnOptions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.tvTitle.text = game.title
        holder.tvGenre.text = game.genre
        holder.ratingBar.rating = game.rating

        holder.btnOptions.setOnClickListener {
            val wrapper = ContextThemeWrapper(holder.itemView.context, R.style.PopupMenu_GamerStyle)
            val popup = PopupMenu(wrapper, holder.btnOptions)
            popup.menuInflater.inflate(R.menu.menu_game_item, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_edit -> {
                        onEditClick(game)
                        true
                    }
                    R.id.item_delete -> {
                        onDeleteClick(game)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int = games.size

    fun updateGames(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
