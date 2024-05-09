package com.example.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserGameAdapter(private val userGames: List<Usergame>) : RecyclerView.Adapter<UserGameAdapter.UserGameViewHolder>() {
    class UserGameViewHolder(gameItemView: View): RecyclerView.ViewHolder(gameItemView){
        val userGameTypeTV: TextView = gameItemView.findViewById(R.id.userGameType)
        val userLocationTV: TextView = gameItemView.findViewById(R.id.userLocation)
        val userTimeTV: TextView = gameItemView.findViewById(R.id.userTime)
        val userGameDateTV: TextView = gameItemView.findViewById(R.id.userGameDate)
        val imageView: ImageView = gameItemView.findViewById(R.id.sportImageUser)
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
        val sport = userGame.sport.toString()
        if (sport == "Soccer"){
            holder.imageView.setImageResource(R.drawable.soccer_ball)
        }
        if (sport == "Basketball"){
            holder.imageView.setImageResource(R.drawable.basketball)
        }
        if (sport == "Volleyball"){
            holder.imageView.setImageResource(R.drawable.volleyball)
        }
        if (sport == "Pickle ball"){
            holder.imageView.setImageResource(R.drawable.app_logo__4_)
        }


        holder.userGameTypeTV.text = userGame.sport
        holder.userLocationTV.text = userGame.location
        holder.userTimeTV.text = userGame.time
        holder.userGameDateTV.text = userGame.date
    }
}
