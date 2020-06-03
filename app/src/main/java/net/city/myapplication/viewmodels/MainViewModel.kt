package net.city.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import net.city.myapplication.models.AppDatabase
import net.city.myapplication.models.UserItem
import net.city.myapplication.models.UserItemDao
import net.city.myapplication.networking.ApiClient
import net.city.myapplication.networking.ApiService
import net.city.myapplication.repositories.PostsDataSource


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val postsLiveData: LiveData<PagedList<UserItem>>
    private val postsdataLiveData: LiveData<PagedList<UserItem>>
    private val userItemDao: UserItemDao
    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    init {
        userItemDao = AppDatabase.getInstaince(application).userdao()
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        postsdataLiveData = init(config).build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    fun getPosts(): LiveData<PagedList<UserItem>> = postsLiveData

    fun getPostsData(): LiveData<PagedList<UserItem>> = postsdataLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, UserItem> {
        val dataSourceFactory = object : DataSource.Factory<Int, UserItem>() {
            override fun create(): PostsDataSource {
                return PostsDataSource(userItemDao)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    private fun init(config: PagedList.Config):
            LivePagedListBuilder<Int, UserItem> {
        val ds = userItemDao.getlist()
        return LivePagedListBuilder(ds, config)
    }
}