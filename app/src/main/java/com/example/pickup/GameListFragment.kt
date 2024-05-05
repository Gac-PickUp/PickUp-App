package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class GameListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ViewGameAdapter
    private lateinit var searchView: SearchView
    private lateinit var gamesList: MutableList<Map<String, Any>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_list, container, false)
        gamesList = mutableListOf()
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter
        adapter = ViewGameAdapter(gamesList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        // Fetch data from Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val game = document.data
                    gamesList.add(game)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to fetch games: ${exception.message}", Toast.LENGTH_SHORT).show()
            }


        adapter.setOnItemClickListener(object : ViewGameAdapter.OnItemClickListener {
            override fun onItemClick(game: Map<String, Any>) {
                game.let {
                    val intent = Intent(requireContext(), GameDetailsActivity::class.java)
                    val jsonGame = Gson().toJson(game)
                    intent.putExtra("game", jsonGame)

                    startActivity(intent)
                }
            }
        })

        return view
    }
}
