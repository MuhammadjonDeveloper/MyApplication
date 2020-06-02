package ke.co.appslab.androidpagingwithcoroutines.repositories

import android.util.Log
import androidx.paging.PageKeyedDataSource
import net.city.myapplication.models.UserItem
import net.city.myapplication.models.UserItemDao
import net.city.myapplication.networking.ApiClient
import ke.co.appslab.androidpagingwithcoroutines.networking.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

const val PAGE_SIZE = 10

class PostsDataSource(
    coroutineContext: CoroutineContext,
    private val userItemDao: UserItemDao
) :
    PageKeyedDataSource<Int, UserItem>() {
    private val apiService = ApiClient.getClient().create(ApiService::class.java)
    private var mPage = 1

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        scope.launch {
            try {
                mPage = 1
                val response = apiService.fetchPosts("asc", mPage, PAGE_SIZE)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.items
                        userItemDao.insertList(listing ?: listOf())
                        Log.e("PostsDataSource", "Failed to fetch data! $mPage")
                        callback.onResult(listing ?: listOf(), mPage, mPage + 10)
                    }
                    response.code() == 403 -> {
                        Log.e("PostsDataSource", "Umuman tushunarsiz")
                    }
                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts("asc", params.key, PAGE_SIZE)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.items
                        userItemDao.insertList(listing ?: listOf())
                        callback.onResult(listing ?: listOf(), mPage + 10)
                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts("asc", mPage, PAGE_SIZE)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.items
                        callback.onResult(listing ?: listOf(), PAGE_SIZE)
                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}