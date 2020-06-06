package net.city.myapplication.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userdao(): UserItemDao


    companion object {
        fun getInstaince(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "db.sqlite")
                .allowMainThreadQueries()
                .build()
        }
    }
}