package com.example.myapplication.ui.more_options

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.ui.user_profile.ProfileActivity
import com.example.myapplication.R
import com.example.myapplication.ui.BaseFragment
import com.example.myapplication.ui.authorization.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_more.view.*

class MoreOptionsFragment : BaseFragment() {


    override fun getLayoutResource() = R.layout.fragment_more
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) {
        init()
    }

    private fun init() {
        rootView!!.select_photo.setOnClickListener{
            openProfile()
        }
        rootView!!.userNameTextView.setOnClickListener{
            openProfile()
        }
        rootView!!.logOutButton.setOnClickListener {
            logOut()
        }


    }

    private fun openProfile(){
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)

    }

    private fun logOut(){
        val alert = AlertDialog.Builder(activity)
        alert.setTitle("log Out")
        alert.setMessage("Are you sure you want to log out?")
        alert.setNegativeButton("No") { _: DialogInterface, _: Int -> }
        alert.setPositiveButton("Yes")
        { _: DialogInterface, _: Int ->
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, RegistrationActivity:: class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)}
        alert.show()
    }
}
