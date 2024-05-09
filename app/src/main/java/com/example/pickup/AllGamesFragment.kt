package com.example.pickup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AllGamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                val gamesList = mutableListOf<Map<String, Any>>()
                for (document in documents) {
                    val game = document.data
                    gamesList.add(game)
                }
                val adapter = GameAdapter(gamesList)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to fetch games: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("AllGamesFragment", "Failed to fetch games", exception)
            }
    }
}