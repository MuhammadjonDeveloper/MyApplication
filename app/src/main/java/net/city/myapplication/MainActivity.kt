package net.city.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import net.city.myapplication.adapters.RedditPostsAdapter
import net.city.myapplication.models.AppDatabase
import net.city.myapplication.models.UserItemDao
import net.city.myapplication.repositories.PostsDataSource
import net.city.myapplication.utils.isOnline
import net.city.myapplication.viewmodels.MainViewModel
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private val redditPostsAdapter = RedditPostsAdapter()
    lateinit var mainViewModel: MainViewModel
    private var isLoading = true
    private lateinit var userItemDao: UserItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //initialize the view model
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        userItemDao = AppDatabase.getInstaince(this).userdao()
//        initializeList()
        swipe_reflash.setOnRefreshListener {
            if (isOnline()) {
//                observeLiveData()
            } else {
                swipe_reflash.isRefreshing = false
            }
        }
        observeLiveData()

//        Paginate.with(list, this)

    }

    private fun observeLiveData() {

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = redditPostsAdapter
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        val handler = Handler()
        val dataSource = PostsDataSource(userItemDao)
        val pagedlist = PagedList.Builder(dataSource, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor { handler.post(it) }
            .build()
        redditPostsAdapter.submitList(pagedlist)
    }

    private fun initializeList() {

    }


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
