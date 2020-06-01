package net.city.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val body: String
)