package com.example.myapplication.ui.stories_feed


import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ui.BaseFragment
import com.example.myapplication.ui.authorization.ItemOnClickUser
import com.example.myapplication.ui.authorization.UserModel
import com.example.myapplication.ui.authorization.UserRecyclerViewAdapter
import com.example.myapplication.ui.user_profile.ProfileActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_users_stories.view.*

class UsersStoriesFragment : BaseFragment() {
    private var listOfUsers = ArrayList<UserModel>()
    private var listOfStories = ArrayList<StoryModel>()
    private lateinit var userAdapter: UserRecyclerViewAdapter
    private lateinit var storyAdapter: StoryRecyclerViewAdapter

    override fun getLayoutResource() = R.layout.fragment_users_stories
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }

    private fun init() {
        setStoryRecyclerView()
        getStory()
        storyAdapter.notifyDataSetChanged()
        setUserRecyclerView()

        userData()
    }

    private fun setUserRecyclerView(){
        userAdapter = UserRecyclerViewAdapter(listOfUsers, object : ItemOnClickUser {
            override fun viewUserProfile(position: Int) {
                val intent = Intent(activity, ProfileActivity::class.java)
                intent.putExtra("first_name",listOfUsers[position].firstName)
                intent.putExtra("last_name",listOfUsers[position].lastName)
//                intent.putExtra("Image",listOfUsers[position].profilePicture!!.toString())
                startActivity(intent)

            }
        })
        rootView!!.recyclerViewUser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        rootView!!.recyclerViewUser.adapter = userAdapter

    }
    private fun userData(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
               p0.children.forEach{
                   val user = it.getValue(UserModel::class.java)
                   if(user != null){
                       listOfUsers.add(0,user)
                       userAdapter.notifyDataSetChanged()
                   }
               }
            }
        })
    }

    private fun getStory(){

        val ref = FirebaseDatabase.getInstance().getReference("/stories")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val story = it.getValue(StoryModel::class.java)
                    if(story != null) {
                        listOfStories.add(0, story)
                        storyAdapter.notifyDataSetChanged()
                    }

                }
            }
        })

    }

    private fun setStoryRecyclerView(){
        storyAdapter = StoryRecyclerViewAdapter(listOfStories, object : ItemOnClick{
            override fun writeStory(position: Int) {
            }

            override fun savePrompt(position: Int) {

            }
        })
        rootView!!.recyclerViewStory.layoutManager = LinearLayoutManager(activity)
        rootView!!.recyclerViewStory.adapter = storyAdapter

    }
}
