package com.example.bitfitapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pickup.GameAdapter
import com.example.pickup.R
import com.example.pickup.Usergame
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class myGamesFragment : Fragment() {
    private val userGames= mutableListOf<Usergame>()
    private lateinit var rvUserGameAdapter: GameAdapter
    private lateinit var rvUserGames: RecyclerView
    private var db= Firebase.firestore



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_games, container, false)


        val layoutManager = LinearLayoutManager(context)
        rvUserGames = view.findViewById(R.id.rv_userGames)
        rvUserGames.layoutManager = layoutManager
        rvUserGameAdapter = GameAdapter(userGames)
        rvUserGames.adapter = rvUserGameAdapter

        // Fetch data from Firestore
        fetchUserGames()

        return view
    }
    private fun fetchUserGames() {






    }

    companion object {
        fun newInstance(): myGamesFragment {
            return myGamesFragment()
        }
    }
}