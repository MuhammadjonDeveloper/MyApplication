package net.city.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.city.myapplication.adapters.RedditPostsAdapter
import net.city.myapplication.utils.isOnline
import net.city.myapplication.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {
    private val redditPostsAdapter = RedditPostsAdapter()
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //initialize the view model
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initializeList()
        swipe_reflash.setOnRefreshListener {
            if (isOnline()) {
                mainViewModel.getDataPosts().observe(this, Observer {
                    redditPostsAdapter.submitList(it)
                })
            } else
                swipe_reflash.isRefreshing = false
        }
    }

    private fun observeLiveData() {
        //observe live data emitted by view model

        mainViewModel.getPosts().observe(this, Observer {
            redditPostsAdapter.submitList(it)
        })
    }

    private fun initializeList() {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = redditPostsAdapter
        mainViewModel.postsLiveData.observe(this, Observer {
            redditPostsAdapter.submitList(it)
        })
    }

    //    override fun onResume() {
//        super.onResume()
//        observeLiveData()
//
//    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
