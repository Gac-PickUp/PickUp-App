package com.example.pickup

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickup.databinding.ActivityGameViewBinding
import com.google.common.reflect.TypeToken
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.firestore
import com.google.gson.Gson

class GameDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameViewBinding

    val db = Firebase.firestore

    val user = Firebase.auth.currentUser




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val jsonGame = intent.getStringExtra("game")
        // val game: Map<String, Any> = Gson().fromJson(jsonGame, object : TypeToken<Map<String, Any>>() {}.type)

        val bundle: Bundle? = intent.extras
        val title: String? = intent.getStringExtra("title")
        val location: String? = intent.getStringExtra("location")
        val date: String? = intent.getStringExtra("date")
        val time: String? = intent.getStringExtra("time")
        val minPlayers: String? = intent.getStringExtra("minPlayers")
        val maxPlayers: String? = intent.getStringExtra("maxPlayers")
        val sport: String? = intent.getStringExtra("sport")



        binding.titleText.text = title
        binding.locationText.text = location
        binding.dateText.text = date

        binding.sportText.text = sport



        val uid = user?.uid


        val playerCountString = "1/$maxPlayers"

        binding.actualPlayerCountText.text = playerCountString

        var playerinGame = playerInGame()

        binding.joinGameButton.setOnClickListener { view ->
            if (playerinGame){
                Toast.makeText(this, "Player is already in the game", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = hashMapOf(
                    "playerID" to uid
                )
                if (uid != null) {
                    val gameInfo = hashMapOf(
                        "gameID" to "game1",
                        "title" to title,
                        "sport" to sport,
                        "location" to location,
                        "minPlayers" to minPlayers,
                        "maxPlayers" to maxPlayers,
                        "time" to time,
                        "date" to date

                    )
                    db.collection("players").document(uid).collection("gamesIn").document("game1").set(gameInfo)

                    db.collection("games").document("game1").collection("playerIDs").document(uid)
                        .set(data)
                        .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Player joined successfully", Toast.LENGTH_SHORT)
                                .show()
                            playerinGame = true
                            if (maxPlayers != null) {
                                updatePlayerCount(maxPlayers.toInt())
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "PLayer failed to join game", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }

        }


        binding.leaveGameButton.setOnClickListener { view ->


            if(playerinGame){
                val documentRef = db.collection("games").document("game1").collection("playerIDs").document(
                    uid.toString()
                )
                val playerRef = uid?.let { db.collection("players").document(it).collection("gamesIn").document("game1") }
                if (playerRef != null) {
                    playerRef.delete()
                }

                documentRef.delete()
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Player removed successfully", Toast.LENGTH_SHORT)
                            .show()
                        playerinGame = false
                        if (maxPlayers != null) {
                            updatePlayerCount(maxPlayers.toInt())
                        }
                    }
                    .addOnFailureListener{ documentReference ->
                        Toast.makeText(this, "Error removing player", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            else{
                Toast.makeText(this, "Player is not currently in the game", Toast.LENGTH_SHORT).show()
            }
        }





    }

    fun playerInGame() : Boolean{

        val uid = user?.uid

        val playerQueryRef = db.collection("games").document("game1").collection("playerIDs")

        val playerQuery = playerQueryRef.whereEqualTo("playerID", uid)

        if(playerQuery == null)
            return false
        else
            return true

    }

    fun updatePlayerCount(maxPlayers : Int){
        val testQuery = db.collection("games").document("game1").collection("playerIDs")

        val countQuery = testQuery.count()


        countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                Log.d(TAG, "Count: ${snapshot.count}")
                binding.actualPlayerCountText.text = "${snapshot.count}/" + maxPlayers
            } else {
                Log.d(TAG, "Count failed: ", task.getException())
            }


        }

    }


}