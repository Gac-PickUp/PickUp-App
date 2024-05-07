package com.example.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserGameAdapter(private val userGames: List<Usergame>) : RecyclerView.Adapter<UserGameAdapter.UserGameViewHolder>() {
    class UserGameViewHolder(gameItemView: View): RecyclerView.ViewHolder(gameItemView){
        val userGameTypeTV: TextView = gameItemView.findViewById(R.id.userGameType)
        val userLocationTV: TextView = gameItemView.findViewById(R.id.userLocation)
        val userAttendeesTV: TextView = gameItemView.findViewById(R.id.userAttendees)
        val userTimeTV: TextView = gameItemView.findViewById(R.id.userTime)
        val userGameDateTV: TextView = gameItemView.findViewById(R.id.userGameDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGameViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val userGameView = inflater.inflate(R.layout.user_game_item, parent, false)
        return UserGameViewHolder(userGameView)
    }

    override fun getItemCount(): Int {
        return userGames.size
    }

    override fun onBindViewHolder(holder: UserGameViewHolder, position: Int) {
        val userGame = userGames[position]
        holder.userGameTypeTV.text = userGame.sport
        holder.userLocationTV.text = userGame.location
        holder.userAttendeesTV.text = userGame.maxPlayers.toString()
        holder.userTimeTV.text = userGame.time
        holder.userGameDateTV.text = userGame.date
    }
}
