package com.example.pickup

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val games: List<Map<String, Any>>, private val clickListener: OnGameItemClickListener) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    interface OnGameItemClickListener {
        fun onGameItemClick(game: Map<String, Any>)
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sportTextView: TextView = itemView.findViewById(R.id.sportTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val minPlayersTextView: TextView = itemView.findViewById(R.id.minPlayersTextView)
        private val maxPlayersTextView: TextView = itemView.findViewById(R.id.maxPlayersTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val teamTextView: TextView = itemView.findViewById(R.id.teamTextView)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(game: Map<String, Any>) {
            sportTextView.text = game["sport"].toString()
            locationTextView.text = game["location"].toString()
            minPlayersTextView.text = game["minPlayers"].toString()
            maxPlayersTextView.text = game["maxPlayers"].toString()
            dateTextView.text = game["date"].toString()
            timeTextView.text = game["time"].toString()
            teamTextView.text = game["team"].toString()

            cardView.setOnClickListener {
                clickListener.onGameItemClick(game)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_game_adaptor, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}