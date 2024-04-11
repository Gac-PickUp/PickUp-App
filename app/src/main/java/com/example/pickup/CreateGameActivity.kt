package com.example.pickup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.Locale

class CreateGameActivity : AppCompatActivity() {

    lateinit var timeButton: Button
    lateinit var dateButton: Button
    var hour: Int = 0
    var minute: Int = 0
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)
        timeButton = findViewById(R.id.timeButton)
        dateButton = findViewById(R.id.dateButton)




        timeButton.setOnClickListener { view ->
            // Do some work here
            popTimePicker(view)
        }
        // on below line we are initializing our variables.

        // on below line we are adding
        // click listener for our button
        dateButton.setOnClickListener {
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    dateButton.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }




        val sports = resources.getStringArray(R.array.sports_list)

        val arrayAdapter = ArrayAdapter(this, R.layout.activity_dropdown_item, sports)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(arrayAdapter)





    }
    fun popTimePicker(view: View) {


        val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            hour = selectedHour
            minute = selectedMinute
            timeButton.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }

        // val style = AlertDialog.THEME_HOLO_DARK

        val timePickerDialog = TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true)
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }



}