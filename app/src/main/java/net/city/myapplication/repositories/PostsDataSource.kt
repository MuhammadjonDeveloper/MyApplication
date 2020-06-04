//package net.city.myapplication.repositories
//
//import android.util.Log
//import androidx.paging.PageKeyedDataSource
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import net.city.myapplication.models.UserItem
//import net.city.myapplication.models.UserItemDao
//import net.city.myapplication.networking.ApiClient
//import net.city.myapplication.networking.ApiService
//import kotlin.coroutines.CoroutineContext
//
//const val PAGE_SIZE = 10
//
//class PostsDataSource(
//    coroutineContext: CoroutineContext,
//    private val userItemDao: UserItemDao
//) :
//    PageKeyedDataSource<Int, UserItem>() {
//    private val apiService = ApiClient.getClient().create(ApiService::class.java)
//    private var mPage = 1
//    private val job = Job()
//    private val scope = CoroutineScope(coroutineContext + job)
//
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, UserItem>
//    ) {
//        scope.launch {
//        try {
//            mPage = 1
//            val response = apiService.fetchPosts("asc", mPage, PAGE_SIZE)
//            when {
//                response.isSuccessful -> {
//                    val listing = response.body()?.items
//                    userItemDao.insertList(listing ?: listOf())
////                        Log.e("PostsDataSource", "Failed to fetch data! $mPage")
//                    Log.d("PostsDataSource", "loadInital ${mPage}")
//                    callback.onResult(listing ?: listOf(), mPage, mPage + 9)
//                }
//                response.code() == 403 -> {
//                    Log.d("PostsDataSource", "loadAfter ")
//                }
//            }
//        } catch (exception: Exception) {
//            Log.e("PostsDataSource", "Failed to fetch data!${exception.message}")
//        }
//
//        }
//
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
//        scope.launch {
//        try {
//            val response = apiService.fetchPosts("asc", params.key, PAGE_SIZE)
//            when {
//                response.isSuccessful -> {
//                    val listing = response.body()?.items
//                    userItemDao.insertList(listing ?: listOf())
//                    Log.d("PostsDataSource", "loadAfter ${params.key}")
//                    callback.onResult(listing ?: listOf(), params.key + 10)
//                }
//            }
//
//        } catch (exception: Exception) {
//            Log.e("PostsDataSource", "Failed to fetch data! ${exception.message}")
//        }
//        }
//
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
//        scope.launch {
//        try {
//            val response =
//                apiService.fetchPosts("asc", params.key, PAGE_SIZE)
//            when {
//                response.isSuccessful -> {
//                    Log.d("PostsDataSource", "Before datasource : ${params.key}")
//                    val listing = response.body()?.items
//                    callback.onResult(listing ?: listOf(), params.key - 10)
//                }
//            }
//
//        } catch (exception: Exception) {
//            Log.e("PostsDataSource", "Failed to fetch data ${exception.message}")
//        }
//        }
//
//    }
//
//}