package net.city.myapplication.models

data class BaseObject<T>(
    val total_count:Long,
    val incomplete_results:Boolean,
    val items:List<T>
)