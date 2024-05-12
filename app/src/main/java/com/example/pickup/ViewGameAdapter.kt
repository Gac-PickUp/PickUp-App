package com.example.pickup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ViewGameAdapter(private val games: List<Map<String, Any>>) :
    RecyclerView.Adapter<ViewGameAdapter.GameViewHolder>(), Filterable {

    private var filteredGames: List<Map<String, Any>> = games
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_view_game_adaptor, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = filteredGames[position]
        val sport = game["sport"]
        holder.bind(game)
        if(sport == "Soccer"){
            holder.imageView.setImageResource(R.drawable.soccer_ball)
        }
        if(sport == "Basketball"){
            holder.imageView.setImageResource(R.drawable.basketball)
        }
        if(sport == "Volleyball"){
            holder.imageView.setImageResource(R.drawable.volleyball)
        }
        if(sport == "Pickle ball"){
            holder.imageView.setImageResource(R.drawable.pickleball)
        }
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim))
    }

    override fun getItemCount(): Int {
        return filteredGames.size
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val imageView: ImageView = itemView.findViewById(R.id.sportImageUser)
        private val sportTextView: TextView = itemView.findViewById(R.id.sportTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTextView)
       // private val minPlayersTextView: TextView = itemView.findViewById(R.id.minPlayersTextView)
       // private val maxPlayersTextView: TextView = itemView.findViewById(R.id.maxPlayersTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        //private val teamTextView: TextView = itemView.findViewById(R.id.teamTextView)
        val cardView: CardView = itemView.findViewById(R.id.cardView)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(game: Map<String, Any>) {
            titleTextView.text = game["title"].toString()
            sportTextView.text = game["sport"].toString()
            locationTextView.text = game["location"].toString()
           // minPlayersTextView.text = game["minPlayers"].toString()
           // maxPlayersTextView.text = game["maxPlayers"].toString()
            dateTextView.text = game["date"].toString()
            timeTextView.text = game["time"].toString()
           // teamTextView.text = game["team"].toString()
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
                        val title = game["title"].toString().lowercase(Locale.getDefault())
                        location.contains(queryString) || sport.contains(queryString)
                                ||title.contains(queryString)
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
