package net.city.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import net.city.myapplication.models.News
import net.city.myapplication.networking.NetworkService
import kotlin.coroutines.CoroutineContext

class NewsDataSourceFactory(
        private val coroutineContext: CoroutineContext,
        private val networkService: NetworkService
)
    : DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(networkService, coroutineContext)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}