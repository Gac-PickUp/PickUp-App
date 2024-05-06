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
import com.example.pickup.UserGameAdapter
import com.example.pickup.Usergame
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class myGamesFragment : Fragment() {
    private lateinit var userGames: MutableList<Usergame>
    private lateinit var rvUserGameAdapter: UserGameAdapter
    private lateinit var rvUserGames: RecyclerView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Initialize RecyclerView and its adapter
        rvUserGames = view?.findViewById(R.id.rv_userGames)!!
        rvUserGames.layoutManager = LinearLayoutManager(context)
        userGames = mutableListOf()
        rvUserGameAdapter = UserGameAdapter(userGames)
        rvUserGames.adapter = rvUserGameAdapter

        // Initialize Firebase instances
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // Fetch user games from Firestore
        fetchUserGames()

        return view
    }
    private fun fetchUserGames() {
        // Assuming userId is initialized correctly
        val userId = fAuth.currentUser?.uid

        userId?.let {
            fStore.collection("players").document(it)
                .collection("gamesIn")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val game = document.toObject(Usergame::class.java)
                        userGames.add(game)
                    }
                    // Notify the adapter of data changes
                    rvUserGameAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    // Handle any errors
                }
        }
    }


    companion object {
        fun newInstance(): myGamesFragment {
            return myGamesFragment()
        }
    }
}