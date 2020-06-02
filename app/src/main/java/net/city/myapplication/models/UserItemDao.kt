package net.city.myapplication.models

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.city.myapplication.models.UserItem

@Dao
interface UserItemDao {

    @Insert
    fun insertList(userItem: List<UserItem>)

    @Query("SELECT * FROM useritem")
    fun getlist():DataSource.Factory<Int, UserItem>
}