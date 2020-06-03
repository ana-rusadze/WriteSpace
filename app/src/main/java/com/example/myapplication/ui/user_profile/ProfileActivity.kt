package com.example.myapplication.ui.user_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

        lateinit var viewPager:ViewPager
        lateinit var tabLayout:TabLayout


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_profile)
            init()
            initViews()
            setStatePageAdapter()
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                    val fm = supportFragmentManager
                    val ft = fm.beginTransaction()
                    val count = fm.backStackEntryCount
                    if (count >= 1) {
                        supportFragmentManager.popBackStack()
                    }
                    ft.commit()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    // setAdapter();


                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                    //   viewPager.notifyAll();
                }
            })
        }

        private fun  initViews(){
            viewPager = findViewById<ViewPager>(R.id.viewPager)
            tabLayout = findViewById<TabLayout>(R.id.tabs)
        }

        private fun setStatePageAdapter(){
            val myViewPageStateAdapter:MyViewPageStateAdapter = MyViewPageStateAdapter(supportFragmentManager)
            myViewPageStateAdapter.addFragment(ProfilePrompts(),"Prompts")
            myViewPageStateAdapter.addFragment(ProfileStories(),"Stories")
            viewPager.adapter=myViewPageStateAdapter
            tabLayout.setupWithViewPager(viewPager,true)

        }
        class MyViewPageStateAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm){
            private val fragmentList:MutableList<Fragment> = ArrayList()
            private val fragmentTitleList:MutableList<String> = ArrayList()

            override fun getItem(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getCount(): Int {
                return fragmentList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return fragmentTitleList[position]
            }

            fun addFragment(fragment:Fragment,title:String){
                fragmentList.add(fragment)
                fragmentTitleList.add(title)

            }
        }

    private fun init(){
        val firstName = intent.getStringExtra("first_name")
        val lastName =intent.getStringExtra("last_name")
        val profilePic = intent.getStringExtra("image")

        firstNameProfile.text = firstName!!.toString()
        lastNameProfile.text =lastName!!.toString()
        if(profilePic != null){
            Picasso.get().load(profilePic.toString()).into(photo_profile)
        }
    }
    }