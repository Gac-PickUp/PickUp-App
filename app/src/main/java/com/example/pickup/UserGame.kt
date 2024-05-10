package com.example.pickup
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class UserGame : AppCompatActivity() {
    private lateinit var createGameButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserGamesAdaptor
    private lateinit var gamesList: MutableList<Map<String, Any>>
    private lateinit var searchView: SearchView
    private lateinit var userId: String
    private lateinit var fAuth: FirebaseAuth



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_game)

        createGameButton = findViewById(R.id.createGameButton)
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gamesList = mutableListOf()
        adapter = UserGamesAdaptor(gamesList)
        recyclerView.adapter = adapter
        fAuth = FirebaseAuth.getInstance()
        userId = "4VnOZMlronOz9YC4kWNb2ol6XXS2" //fAuth.currentUser?.uid ?: "null"


        val db = FirebaseFirestore.getInstance()
        db.collection("players").document(userId)
            .collection("gamesIn")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val game = document.data
                    gamesList.add(game)
                    //Log.d(game.toString(), "game")
                }
                //val gameList = gamesList
                //Log.d(gameList.toString(), "gameList")
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Failed to fetch games: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
                 Log.e("ViewGameActivity", "Failed to fetch games", exception)
            }


        createGameButton.setOnClickListener {
            startActivity(Intent(this, CreateGameActivity::class.java))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        // Set item click listener
        adapter.setOnItemClickListener(object : UserGamesAdaptor.OnItemClickListener {
            override fun onItemClick(game: Map<String, Any>) {
                game.let {
                    val intent = Intent(this@UserGame, GameDetailsActivity::class.java)
                    //val jsonGame = Gson().toJson(game)
                    //intent.putExtra("game", jsonGame)
                    //val intent = Intent(this, SingleGameViewActivity::class.java)
                    intent.putExtra("title", game["title"].toString())
                    intent.putExtra("sport", game["sport"].toString())
                    intent.putExtra("location", game["location"].toString())
                    intent.putExtra("minPlayers", game["minPlayers"].toString())
                    intent.putExtra("maxPlayers", game["maxPlayers"].toString())
                    intent.putExtra("date", game["date"].toString())
                    intent.putExtra("time", game["time"].toString())

                    startActivity(intent)
                }
            }
        })

    }
}
