package net.city.myapplication.models

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(userItem: List<UserItem>)

    @Query("SELECT * FROM useritem")
    fun getlist(): DataSource.Factory<Int, UserItem>

//    @Query("SELECT login FROM News")
//    fun getlistString():List<String>
}