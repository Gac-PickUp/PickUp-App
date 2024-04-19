package com.example.pickup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pickup.databinding.ActivityCreateGameBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Locale

class CreateGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGameBinding
    var hour: Int = 0
    var minute: Int = 0
    private val calendar = Calendar.getInstance()

    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        binding = ActivityCreateGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Creating the dropdown menu for choosing a sport
        val sports = resources.getStringArray(R.array.sports_list)

        val sportsArrayAdapter = ArrayAdapter(this, R.layout.activity_dropdown_item, sports)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(sportsArrayAdapter)

        //Creating the dropdown menu for choosing a group or public
        val groupsList = resources.getStringArray(R.array.groups_choice_list)

        val groupsArrayAdapter = ArrayAdapter(this, R.layout.activity_dropdown_item, groupsList)

        val groupsAutocomplete = findViewById<AutoCompleteTextView>(R.id.chooseTeamAutoComplete)

        groupsAutocomplete.setAdapter(groupsArrayAdapter)




        val user = Firebase.auth.currentUser
        val uid = user?.uid
        binding.createGameButton.setOnClickListener{view ->
            val newGameRef = db.collection("games").document()

            val gameInfo = hashMapOf(
                "authorID" to uid,
                "title" to binding.titleText.text.toString(),
                "sport" to binding.autoCompleteTextView.text.toString(),
                "location" to binding.locationText.text.toString(),
                "minPlayers" to binding.minPlayersText.text.toString().toIntOrNull(),
                "maxPlayers" to binding.maxPlayersText.text.toString().toIntOrNull(),
                "date" to binding.dateButton.text.toString(),
                "time" to binding.timeButton.text.toString(),
                "team" to binding.chooseTeamAutoComplete.text.toString()
            )
            val playerinfo = hashMapOf(
                "playerID" to uid
            )

            newGameRef
                .set(gameInfo)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Game Created Successfully", Toast.LENGTH_SHORT).show()
                    if (uid != null) {
                        newGameRef.collection("playerID").add(playerinfo)
                    }
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



}