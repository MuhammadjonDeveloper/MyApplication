package net.city.myapplication.networking

import net.city.myapplication.models.BaseObject
import net.city.myapplication.models.UserItem
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("users")
    suspend fun getNews(
        @Query("q") sort: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): retrofit2.Response<BaseObject<UserItem>>

    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/search/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(NetworkService::class.java)
        }
    }

}