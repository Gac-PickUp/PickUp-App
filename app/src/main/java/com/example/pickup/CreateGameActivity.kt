package com.example.pickup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickup.databinding.ActivityCreateGameBinding
import com.example.pickup.databinding.ActivityCreateGameRealBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Locale

private val TAG: String = CreateGameActivity::class.java.getName()
class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGameRealBinding
    var hour: Int = 0
    var minute: Int = 0
    private val calendar = Calendar.getInstance()

    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameRealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //updatePlayerCount()
        // Creating the dropdown menu for choosing a sport
        val sports = resources.getStringArray(R.array.sports_list)

        val sportsArrayAdapter = ArrayAdapter(this, R.layout.activity_dropdown_item, sports)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(sportsArrayAdapter)

        //Creating the dropdown menu for choosing a group or public

        /*
        val groupsList = resources.getStringArray(R.array.groups_choice_list)

        val groupsArrayAdapter = ArrayAdapter(this, R.layout.activity_dropdown_item, groupsList)

        val groupsAutocomplete = findViewById<AutoCompleteTextView>(R.id.chooseTeamAutoComplete)

        groupsAutocomplete.setAdapter(groupsArrayAdapter)


         */

        val user = Firebase.auth.currentUser
        val uid = user?.uid


        // Test for letting user join a game

        /*
        binding.testAddButton.setOnClickListener { view ->
            val playerQueryRef = db.collection("games").document("game1").collection("playerIDs")

            val playerQuery = playerQueryRef.whereEqualTo("playerID", uid)

            if (playerQuery == null) {
                val data = hashMapOf(
                    "playerID" to uid
                )
                db.collection("games").document("game1").collection("playerIDs")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(this, "Player joined successfully", Toast.LENGTH_SHORT)
                            .show()
                        updatePlayerCount()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "PLayer failed to join game", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            else{
                Toast.makeText(this, "This player already exists", Toast.LENGTH_SHORT).show()
            }
        }

         */




        binding.minPlayersText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }


            override fun afterTextChanged(p0: Editable?) {
                checkPlayerCountMin()
            }


        })

        binding.maxPlayersText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                checkPlayerCountMax()
            }

        })


        binding.createGameButton.setOnClickListener{view ->
            val ranNum = (0..10000000).random()
            val ranNumString = ranNum.toString()
            val newGameRef = db.collection("games").document(ranNumString)
            val gameInfo = hashMapOf(
                "authorID" to uid,
                "gameid" to ranNumString,
                "title" to binding.titleText.text.toString(),
                "sport" to binding.autoCompleteTextView.text.toString(),
                "location" to binding.locationText.text.toString(),
                "minPlayers" to binding.minPlayersText.text.toString().toIntOrNull(),
                "maxPlayers" to binding.maxPlayersText.text.toString().toIntOrNull(),
                "date" to binding.dateButton.text.toString(),
                "time" to binding.timeButton.text.toString()
                //"team" to binding.chooseTeamAutoComplete.text.toString()
            )
            val playerinfo = hashMapOf(
                "playerID" to uid
            )


            newGameRef
                .set(gameInfo)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Game Created Successfully", Toast.LENGTH_SHORT).show()
                    if (uid != null) {
                        newGameRef.collection("playerIDs").document(uid).set(playerinfo)
                        db.collection("players").document(uid).collection("gamesIn").document(ranNumString).set(gameInfo)
                    }

                    val intent = Intent(this, ViewGameActivity::class.java)

                    startActivity(intent)
                    /*
                    val intent = Intent(this, SingleGameViewActivity::class.java)
                    intent.putExtra("title", binding.titleText.text.toString())
                    intent.putExtra("sport", binding.autoCompleteTextView.text.toString())
                    intent.putExtra("location",  binding.locationText.text.toString())
                    intent.putExtra("minPlayers", binding.minPlayersText.text.toString())
                    intent.putExtra("maxPlayers", binding.maxPlayersText.text.toString())
                    intent.putExtra("date", binding.dateButton.text.toString())
                    intent.putExtra("time", binding.timeButton.text.toString())
                    //intent.putExtra("team", binding.chooseTeamAutoComplete.text.toString())
                    startActivity(intent)
                    */


                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to create game", Toast.LENGTH_SHORT).show()
                }

        }



        binding.timeButton.setOnClickListener { view ->

            popTimePicker(view)
        }

        // click listener for our button
        binding.dateButton.setOnClickListener {

            val c = Calendar.getInstance()


            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    binding.dateButton.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // to display our date picker dialog.
            datePickerDialog.show()
        }


    }
    fun popTimePicker(view: View) {


        val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            hour = selectedHour
            minute = selectedMinute
            binding.timeButton.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }

        val timePickerDialog = TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, false)
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }

    fun checkPlayerCountMin (){

        val minPlayers = binding.minPlayersText.text.toString()
        val maxPlayers = binding.maxPlayersText.text.toString()
        if (minPlayers.isNotEmpty()  && maxPlayers.isNotEmpty()){
            if(minPlayers.toInt() > maxPlayers.toInt()){
                binding.minPlayersText.setError("Min players bigger than max players")
            }
            else
                binding.minPlayersText.setError(null)
        }
    }

    fun checkPlayerCountMax (){

        val minPlayers = binding.minPlayersText.text.toString()
        val maxPlayers = binding.maxPlayersText.text.toString()
        if (minPlayers.isNotEmpty()  && maxPlayers.isNotEmpty()){
            if(minPlayers.toInt() > maxPlayers.toInt()){
                binding.maxPlayersText.setError("Max players smaller than min players")
            }
            else
                binding.maxPlayersText.setError(null)
        }
    }


    /*
    fun updatePlayerCount(){
        val testQuery = db.collection("games").document("game1").collection("playerIDs")

        val countQuery = testQuery.count()


        countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                Log.d(TAG, "Count: ${snapshot.count}")
                binding.testPlayerCount.text = "${snapshot.count}"
            } else {
                Log.d(TAG, "Count failed: ", task.getException())
            }


        }

    }

     */



}