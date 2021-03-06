package net.city.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_news_list.*
import net.city.myapplication.adapter.NewsListAdapter
import net.city.myapplication.viewmodels.NewsListViewModel
import net.city.myapplication.viewmodels.State


class MainActivity : AppCompatActivity() {
    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var viewModel: NewsListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        newsListAdapter = NewsListAdapter {
            viewModel.retry()
        }
        recycler_view.adapter = newsListAdapter
        viewModel.newsList.observe(this,
            Observer {
                newsListAdapter.submitList(it)
            })
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
          if (state==State.LOADING){
              newsListAdapter.setState(state)
          }else{
              newsListAdapter.setState(State.DONE)
          }
        })
    }
}