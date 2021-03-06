package net.city.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*
import net.city.myapplication.R
import net.city.myapplication.models.UserItem

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(news: UserItem?) {
        if (news != null) {
            itemView.txt_news_name.text = news.login
            if (!news.avatar_url.isNullOrEmpty())
                Picasso.get().load(news.avatar_url).into(itemView.img_news_banner)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_news, parent, false)
            return NewsViewHolder(view)
        }
    }
}