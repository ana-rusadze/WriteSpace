package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.ViewPagerAdapter
import com.example.myapplication.ui.authorization.RegistrationActivity
import com.example.myapplication.ui.stories_feed.UsersStoriesFragment
import com.example.myapplication.ui.prompts_feed.PromptsFeedFragment
import com.example.myapplication.ui.more_options.MoreOptionsFragment
import com.example.myapplication.ui.user_profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        init()
        verifyUserLoggedIn()
    }

    private fun verifyUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid ==null){
            val intent = Intent(this, RegistrationActivity:: class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun init() {
        val fragments = mutableListOf<Fragment>()
        fragments.add(PromptsFeedFragment())
        fragments.add(UsersStoriesFragment())
        fragments.add(MoreOptionsFragment())
        viewPager.offscreenPageLimit =3
        viewPager.adapter = ViewPagerAdapter(
            supportFragmentManager,
            fragments
        )
        viewPagerIssues()
    }

    private fun viewPagerIssues() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                nav_view.menu.getItem(position).isChecked = true

            }
        })

        nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_dashboard -> viewPager.currentItem = 1
                R.id.navigation_notifications -> viewPager.currentItem =2
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, ProfileActivity:: class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}