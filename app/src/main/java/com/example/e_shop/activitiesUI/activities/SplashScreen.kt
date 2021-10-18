package com.example.e_shop.activitiesUI.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.e_shop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val user = Firebase.auth.currentUser

        val SPLASH_TIME = 2500
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                /*
                if (user != null) {
                    // User is signed in
                    startActivity(Intent(this, UserProfileActivity::class.java))
                } else {
                    // No user is signed in
                    startActivity(Intent(this, LoginActivity::class.java))
                }*/
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }, SPLASH_TIME.toLong()
        )
    }
}