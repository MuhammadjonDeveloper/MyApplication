package net.city.myapplication.utils

import androidx.recyclerview.widget.DiffUtil
import net.city.myapplication.models.UserItem

class DiffUtilCallBack : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.score == newItem.score
    }

}