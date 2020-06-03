package com.example.myapplication.ui.authorization

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.BottomNavigationActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LogInActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        animations()
        init()

    }

    private fun init() {
        readData()
        logInButton.setOnClickListener {
            closeKeyBoard()
            logIn()
        }
        signUpTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun closeKeyBoard() {
        val imm: InputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun logIn() {
        val email = logEmailField.text.toString()
        val password = logPasswordField.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("SignUp", "logged user: ${it.result!!.user!!.uid}")
                    Toast.makeText(baseContext, "Log in is successful", Toast.LENGTH_SHORT)
                        .show()
                    saveData()
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "${it.message}", Toast.LENGTH_SHORT).show()
            }


    }


    private fun saveData() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString("email", logEmailField.text.toString())
        edit.putString("password", logPasswordField.text.toString())
        edit.apply()

    }

    private fun readData() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        logEmailField.setText(sharedPreferences.getString("email", ""))
        logPasswordField.setText(sharedPreferences.getString("password", ""))

    }

    private fun animations() {
        val animation = AnimationUtils.loadAnimation(this,
            R.anim.zoom_in
        )
        select_photo_log.startAnimation(animation)
        val animation2 = AnimationUtils.loadAnimation(this,
            R.anim.fade_in
        )
        logEmailField.startAnimation(animation2)
        logPasswordField.startAnimation(animation2)
        forgotPassword.startAnimation(animation2)

    }


}
