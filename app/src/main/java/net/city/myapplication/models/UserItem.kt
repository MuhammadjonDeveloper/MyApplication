package net.city.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("articles")
    val news: List<News>
)
@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title: String,
    @SerializedName("urlToImage")
    val image: String?
)