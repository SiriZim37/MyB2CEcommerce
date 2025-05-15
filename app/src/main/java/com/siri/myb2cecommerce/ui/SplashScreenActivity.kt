package com.siri.myb2cecommerce.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.siri.myb2cecommerce.ui.home.HomeActivity
import com.siri.myb2cecommerce.ui.home.LoginActivity
import com.siri.myb2cecommerce.utils.FirebaseUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        Handler().postDelayed({

            checkUser()

        }, 1000)
    }

    private fun checkUser() {

        if(FirebaseUtils.firebaseUser?.isEmailVerified == true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        // To do : this Code Valid > uncomment later
        if(FirebaseUtils.firebaseUser != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(FirebaseUtils.firebaseUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Todo :  MOCK
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
//        finish()

    }
}