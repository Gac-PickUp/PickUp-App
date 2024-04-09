package com.example.pickup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class LandingPage : AppCompatActivity() {

    private lateinit var  lottie: LottieAnimationView;
    private lateinit var  loadLottie: LottieAnimationView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)


        lottie=findViewById(R.id.lottie)
        loadLottie=findViewById(R.id.loading)
        animateLottie()
    }

    private fun animateLottie() {
        // Get the ViewPropertyAnimator for the LottieAnimationView
        val animator: ViewPropertyAnimator = lottie.animate()
        val loading:ViewPropertyAnimator = loadLottie.animate()

        // Set the translationX to move the LottieAnimationView horizontally
        animator.translationX(2000f)

        // Set duration in milliseconds
        animator.setDuration(2000)
        loading.setDuration(3000)

        // Set start delay in milliseconds
        animator.setStartDelay(2900)

        // Start the animation
        loading.start()
        animator.start()
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Finish the SplashScreen activity to prevent it from being shown again when back button is pressed
        }, 4000) // Delay duration equals to animation duration plus start delay
    }
}
