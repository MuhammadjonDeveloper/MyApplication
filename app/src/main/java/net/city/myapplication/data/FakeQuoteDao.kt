package net.city.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface FakeQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserQuate(quote: Quote)

    @Query("SELECT * FROM quote")
    fun getQuoteList(): LiveData<List<Quote>>

    @Delete
    fun deleteQuote(quote: Quote)
}