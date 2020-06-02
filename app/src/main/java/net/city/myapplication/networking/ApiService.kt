package ke.co.appslab.androidpagingwithcoroutines.networking

import net.city.myapplication.models.BaseObject
import net.city.myapplication.models.UserItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun fetchPosts(
        @Query("q") sort: String,
        @Query("page") page:Int,
        @Query("per_page") before: Int=0
    ): Response<BaseObject<UserItem>>
}