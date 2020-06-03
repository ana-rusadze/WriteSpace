package com.example.myapplication.ui.authorization

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.BottomNavigationActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_PICK_CODE = 0
        private const val PERMISSION_CODE = 1
    }

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        animations()
        init()
    }

    private fun init() {
        registrationButton.setOnClickListener {
            closeKeyBoard()
            registration()
        }
        logInTextView.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        setImageButton.setOnClickListener {
            checkPermission()
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


    private fun registration() {
        firstName = firstNameEditText.text.toString()
        lastName = lastNameEditText.text.toString()
        email = regEmailField.text.toString()
        password = regPasswordField.text.toString()
        val password2 = regPasswordField2.text.toString()


        if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Please Fill all fields!", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != password2) {
            Toast.makeText(this, "Passwords Don't match!", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("SignUp", "created user: ${it.result!!.user!!.uid}")
                    Toast.makeText(
                        baseContext,
                        "Welcome to WriteSpace, $firstName!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    uploadImageToFirebaseStorage()
                    saveUserToFirebaseDatabase(imageUrl)
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions,
                    PERMISSION_CODE
                )
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    private var selectedImage: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            selectedImage = data.data
            select_photo_reg.setImageURI(selectedImage)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadImageToFirebaseStorage() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        if (selectedImage != null) {
            ref.putFile(selectedImage!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        d("registerActivity", "file location: $it")
                        imageUrl = it.toString()
                    }
                }
        }

    }

    private fun saveUserToFirebaseDatabase(image: String?) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = UserModel()
        user.id = uid
        user.firstName = firstName
        user.lastName = lastName
        user.email = email
        user.password = password
        if(image != null){
            user.profilePicture = image
        }else user.profilePicture =
            R.drawable.ic_person_orange_80dp

        ref.setValue(user)
    }

    private fun animations() {
        val animation = AnimationUtils.loadAnimation(this,
            R.anim.zoom_in
        )
        photo_layout.startAnimation(animation)
        val animation2 = AnimationUtils.loadAnimation(this,
            R.anim.fade_in
        )
        firstNameEditText.startAnimation(animation2)
        lastNameEditText.startAnimation(animation2)
        regEmailField.startAnimation(animation2)
        regPasswordField.startAnimation(animation2)
        regPasswordField2.startAnimation(animation2)

    }

}

