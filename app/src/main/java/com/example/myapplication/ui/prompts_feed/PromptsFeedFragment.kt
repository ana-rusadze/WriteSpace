package com.example.myapplication.ui.prompts_feed

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.HttpRequest.REQUEST
import com.example.myapplication.ui.BaseFragment
import com.example.myapplication.ui.BottomNavigationActivity
import com.example.myapplication.ui.stories_feed.WriteStoryActivity
import kotlinx.android.synthetic.main.fragment_prompts_feed.*
import kotlinx.android.synthetic.main.fragment_prompts_feed.view.*
import kotlinx.android.synthetic.main.prompt_recyclerview_layout.*
import org.json.JSONObject


class PromptsFeedFragment : BaseFragment() {
    private var listOfPrompts = ArrayList<PromptModel>()
    private lateinit var adapter: PromptRecyclerViewAdapter
    private var isLoading = false

    override fun getLayoutResource() = R.layout.fragment_prompts_feed
    override fun start(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        init()
    }


    private fun init() {
        setRecyclerView()

        rootView!!.loadingTextView.visibility = View.VISIBLE
        getPrompts()
        rootView!!.loadingTextView.visibility = View.GONE
        rootView!!.swipeRefreshLayout.setOnRefreshListener {
            rootView!!.swipeRefreshLayout.isRefreshing = true
            refresh()

            Handler().postDelayed({
                rootView!!.loadingTextView.visibility = View.GONE
                rootView!!.swipeRefreshLayout.isRefreshing = false
                adapter.notifyDataSetChanged()

            }, 2000)
        }

    }


    private fun setRecyclerView(){
        adapter = PromptRecyclerViewAdapter(listOfPrompts, object : ItemOnClick {
            override fun writeStory(position: Int) {
                val intent = Intent(activity, WriteStoryActivity::class.java)
                intent.putExtra("prompt", listOfPrompts[position].text)
                startActivityForResult(intent, 2)
            }
            override fun savePrompt(position: Int) {
            } })

        rootView!!.recyclerViewPrompts.layoutManager = LinearLayoutManager(activity)
        rootView!!.recyclerViewPrompts.adapter = adapter
    }
    private fun clear() {
        listOfPrompts.clear()
        adapter.notifyItemRangeRemoved(0, listOfPrompts.size)
    }

    private fun refresh() {
        clear()
        getPrompts()
        rootView!!.loadingTextView.visibility = View.VISIBLE

    }

    private fun getPrompts() {
        for (i in 0..7) {
            HttpRequest.getRequest(REQUEST, object : CustomCallback {
                override fun onFailure(response: String) {
                    Toast.makeText(
                        activity as BottomNavigationActivity,
                        "check your internet connection and try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(response: String) {
                    val json = JSONObject(response)
                    if (json.has("english")) {
                        val value = json.getString("english")
                        if (value != ".") {
                            val prompt = PromptModel()
                            prompt.text = value
                            listOfPrompts.add(0, prompt)
                            adapter.notifyDataSetChanged()
                        }

                    }
                }
            })
        }

    }
}
