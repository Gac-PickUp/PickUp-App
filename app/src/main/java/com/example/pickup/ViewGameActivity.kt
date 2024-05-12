package com.example.pickup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ViewGameActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_game)

        supportFragmentManager.beginTransaction()
            .replace(R.id.gameListContainer, GameListFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.buttonContainer, ButtonFragment())
            .commit()
    }
}