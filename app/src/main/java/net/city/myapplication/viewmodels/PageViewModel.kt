package net.city.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import net.city.myapplication.networking.ApiService
import net.city.myapplication.models.AppDatabase
import net.city.myapplication.models.UserItemDao
import net.city.myapplication.networking.ApiClient

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val apiClient = ApiClient.getClient().create(ApiService::class.java)
    private val userDao: UserItemDao

    init {
        userDao = AppDatabase.getInstaince(application).userdao()
    }


}