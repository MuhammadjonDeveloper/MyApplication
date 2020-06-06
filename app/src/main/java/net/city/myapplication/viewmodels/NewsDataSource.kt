package net.city.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.city.myapplication.models.UserItem
import net.city.myapplication.networking.NetworkService
import net.city.myapplication.viewmodels.State.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class NewsDataSource(
    private val networkService: NetworkService,
    coroutineContext: CoroutineContext) : PageKeyedDataSource<Int, UserItem>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryAction: (() -> Any)? = null
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UserItem>
    ) {
        updateState(LOADING)
        scope.launch {
            try {
                val res = networkService.getNews("asc", 1, params.requestedLoadSize)
                if (res.isSuccessful) {
                    val data = res.body()!!.items
                    updateState(DONE)
                    callback.onResult(data, null, 10)
                } else if (res.code() == 403) {
                    //todo
                }
            } catch (e: IOException) {
                updateState(ERROR)
                retryAction = { loadInitial(params, callback) }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
        updateState(LOADING)
        scope.launch {
            try {
                val res = networkService.getNews("asc", params.key, params.requestedLoadSize)
                if (res.isSuccessful) {
                    val data = res.body()!!.items
                    updateState(DONE)
                    callback.onResult(data, params.key + 10)
                } else if (res.code() == 403) {
                    //todo
                }
            } catch (e: IOException) {
                updateState(ERROR)
                retryAction = { loadAfter(params, callback) }
            }


        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserItem>) {
    }
    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        retryAction?.invoke()
    }

    fun cancel() {
        job.cancel()
    }
}