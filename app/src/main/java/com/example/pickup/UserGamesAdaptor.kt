package com.example.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class UserGamesAdaptor(private val games: List<Map<String, Any>>) :
    RecyclerView.Adapter<UserGamesAdaptor.GameViewHolder>(), Filterable {

    private var filteredGames: List<Map<String, Any>> = games
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_game_adaptor, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = filteredGames[position]
        holder.bind(game)
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim))
    }

    override fun getItemCount(): Int {
        return filteredGames.size
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val sportTextView: TextView = itemView.findViewById(R.id.sportTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
        private val minPlayersTextView: TextView = itemView.findViewById(R.id.minPlayersTextView)
        private val maxPlayersTextView: TextView = itemView.findViewById(R.id.maxPlayersTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val teamTextView: TextView = itemView.findViewById(R.id.teamTextView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(game: Map<String, Any>) {
            sportTextView.text = game["sport"].toString()
            locationTextView.text = game["location"].toString()
            minPlayersTextView.text = game["minPlayers"].toString()
            maxPlayersTextView.text = game["maxPlayers"].toString()
            dateTextView.text = game["date"].toString()
            timeTextView.text = game["time"].toString()
            teamTextView.text = game["team"].toString()
        }

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(filteredGames[adapterPosition])
        }
    }

    interface OnItemClickListener {
        fun onItemClick(game: Map<String, Any>)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString().lowercase(Locale.getDefault())
                filteredGames = if (queryString.isEmpty()) {
                    games
                } else {
                    games.filter { game ->
                        val location = game["location"].toString().lowercase(Locale.getDefault())
                        val sport = game["sport"].toString().lowercase(Locale.getDefault())
                        location.contains(queryString) || sport.contains(queryString)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredGames
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredGames = results?.values as List<Map<String, Any>>
                notifyDataSetChanged()
            }
        }
    }
}
