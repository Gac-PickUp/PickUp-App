package com.example.pickup

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class GameAdapter(private val games: List<Map<String, Any>>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {


    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sportTextView: TextView = itemView.findViewById(R.id.sportTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        //private val minPlayersTextView: TextView = itemView.findViewById(R.id.minPlayersTextView)
       // private val maxPlayersTextView: TextView = itemView.findViewById(R.id.maxPlayersTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        //private val teamTextView: TextView = itemView.findViewById(R.id.teamTextView)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)


        private val imageView: ImageView = itemView.findViewById(R.id.sportImage)


        fun bind(game: Map<String, Any>) {

            val sport = game["sport"].toString()

            if (sport == "Soccer"){
                imageView.setImageResource(R.drawable.soccer_ball)
            }
            if (sport == "Basketball"){
                imageView.setImageResource(R.drawable.basketball)
            }
            if (sport == "Volleyball"){
                imageView.setImageResource(R.drawable.volleyball)
            }
            if (sport == "Pickle ball"){
                imageView.setImageResource(R.drawable.pickleball)
            }

            sportTextView.text = sport

            locationTextView.text = game["location"].toString()
           // minPlayersTextView.text = game["minPlayers"].toString()
           // maxPlayersTextView.text = game["maxPlayers"].toString()
            titleTextView.text = game["title"].toString()
            dateTextView.text = game["date"].toString()
            timeTextView.text = game["time"].toString()
            //teamTextView.text = game["team"].toString()
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


