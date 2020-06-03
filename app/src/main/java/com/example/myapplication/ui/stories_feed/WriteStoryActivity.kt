package com.example.myapplication.ui.stories_feed

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.ui.BottomNavigationActivity
import com.example.myapplication.ui.authorization.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write_story.*

class WriteStoryActivity : AppCompatActivity() {

    private lateinit var currentPrompt: String
    private lateinit var story: String
    private var author = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_story)
        supportActionBar?.title = "Write a Story"
        init()
    }

    override fun onBackPressed() {
        if (storyEditText.text.isNotEmpty()){
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Story changes won't be saved!")
            alert.setMessage("Are you sure you want to leave?")
            alert.setNegativeButton("No, I'll finish it") { _: DialogInterface, _: Int -> }
            alert.setPositiveButton("Yes, don't save it") { _: DialogInterface, _: Int ->
                super.onBackPressed() }
            alert.show()
        }else super.onBackPressed()
    }

    private fun init(){
        val value = intent.getStringExtra("prompt")
        promptText.text = value!!.toString()
        currentPrompt = promptText.text.toString()

        publishButton.setOnClickListener {
            story = storyEditText.text.toString()
            if (story.isEmpty()) {
                Toast.makeText(this, "Text field must not be empty!", Toast.LENGTH_SHORT).show()
            }else{
                saveStoryToFirebaseDatabase()
                Toast.makeText(this, "Your story has been published!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UsersStoriesFragment::class.java)
                intent.putExtra("story",story)
                intent.putExtra("author", author)
                intent.putExtra("currentPrompt", currentPrompt)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
            }
        }


    }
    private var count =1
    private fun saveStoryToFirebaseDatabase() {
        val storyModel = StoryModel()
        val ref = FirebaseDatabase.getInstance().getReference("/stories/$count")
        count++
        storyModel.author = author
        storyModel.prompt = currentPrompt
        storyModel.story = story
        ref.setValue(storyModel)
    }


}
