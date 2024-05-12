package com.example.pickup
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.pickup.CreateGameActivity
import com.example.pickup.ViewGameActivity

class ButtonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val createGameButton: Button = view.findViewById(R.id.createGameButton)
        val myGamesButton: Button = view.findViewById(R.id.myGamesButton)
        val allGamesButton: Button = view.findViewById(R.id.allGamesButton)

        createGameButton.setOnClickListener {
            startActivity(Intent(requireContext(), CreateGameActivity::class.java))
        }

        myGamesButton.setOnClickListener {
            startActivity(Intent(requireContext(), UserGame::class.java))
        }

        allGamesButton.setOnClickListener {
            startActivity(Intent(requireContext(), ViewGameActivity::class.java))
        }

        return view
    }
}


