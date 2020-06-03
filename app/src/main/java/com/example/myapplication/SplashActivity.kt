package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.myapplication.ui.authorization.LogInActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animation()
    }

    private val runnable = Runnable{
        openLogInActivity()
    }

    override fun onStart(){
        super.onStart()
        Handler().postDelayed(runnable, 3000)
    }
    override fun onPause(){
        super.onPause()
        Handler().removeCallbacks(runnable)
    }

    private fun openLogInActivity(){
        val intent = Intent(this,
            LogInActivity::class.java )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun animation(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        splashscreen_logo.startAnimation(animation)
    }
}
