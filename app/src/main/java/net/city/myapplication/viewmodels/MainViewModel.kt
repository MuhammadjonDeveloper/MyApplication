package net.city.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ke.co.appslab.androidpagingwithcoroutines.repositories.PostsDataSource
import kotlinx.coroutines.Dispatchers
import net.city.myapplication.models.AppDatabase
import net.city.myapplication.models.UserItem
import net.city.myapplication.models.UserItemDao
import net.city.myapplication.utils.isOnline

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var postsLiveData: LiveData<PagedList<UserItem>>

    //    var postsdataLiveData: LiveData<PagedList<UserItem>>
    private val userItemDao: UserItemDao
    private val isConnected by lazy { application.isOnline() }

    init {
        userItemDao = AppDatabase.getInstaince(application).userdao()
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
//        postsdataLiveData=init(config).build()
    }

    fun getPosts(): LiveData<PagedList<UserItem>> = postsLiveData
//    fun getDataPosts(): LiveData<PagedList<UserItem>> = postsdataLiveData


    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, UserItem> {
        val dataSourceFactory = object : DataSource.Factory<Int, UserItem>() {
            override fun create(): PostsDataSource {
                return PostsDataSource(Dispatchers.IO, userItemDao)
            }
        }
        return if (isConnected) {
            LivePagedListBuilder(dataSourceFactory, config)
        } else {
            val ds = userItemDao.getlist()
            LivePagedListBuilder(ds, config)
        }

//    private fun init(config: PagedList.Config):
//
//    }
    }
}