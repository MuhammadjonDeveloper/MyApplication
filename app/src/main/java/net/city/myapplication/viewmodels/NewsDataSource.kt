package net.city.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.city.myapplication.models.News
import net.city.myapplication.networking.NetworkService
import net.city.myapplication.viewmodels.State.DONE
import net.city.myapplication.viewmodels.State.ERROR
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class NewsDataSource(
    private val networkService: NetworkService,
    coroutineContext: CoroutineContext
) : PageKeyedDataSource<Int, News>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryAction: (() -> Any)? = null
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val res = networkService.getNews(1, params.requestedLoadSize)
                if (res.isSuccessful) {
                    updateState(DONE)
                    val data = res.body()!!.news
                    callback.onResult(data, null, 2)
                } else if (res.code() == 403) {
                    //todo
                }
            } catch (e: IOException) {
                updateState(ERROR)
                retryAction = { loadInitial(params, callback) }
            }


        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val res = networkService.getNews(params.key, params.requestedLoadSize)
                if (res.isSuccessful) {
                    updateState(DONE)
                    val data = res.body()!!.news
                    callback.onResult(data, params.key + 1)
                } else if (res.code() == 403) {
                    //todo
                }
            } catch (e: IOException) {
                updateState(ERROR)
                retryAction = { loadAfter(params, callback) }
            }


        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        retryAction?.invoke()
    }

    fun cancel(){
        job.cancel()
    }

//    private fun setRetry(action: Action) {
//        retryCompletable = if (action == null) null else Completable.fromAction(action)
//    }
}