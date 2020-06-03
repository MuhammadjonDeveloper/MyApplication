package net.city.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import net.city.myapplication.models.AppDatabase

class MyTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.getInstaince(this).userdao()
        val list = dao.getlistString()
        list.forEach {
            Log.d("TAG_LOG", "login : $it")
        }
    }
}