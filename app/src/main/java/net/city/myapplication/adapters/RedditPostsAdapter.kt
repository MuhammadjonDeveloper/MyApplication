package net.city.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.city.myapplication.models.UserItem
import net.city.myapplication.utils.DiffUtilCallBack
import kotlinx.android.synthetic.main.adapter_row.view.*
import net.city.myapplication.R

class RedditPostsAdapter : PagedListAdapter<UserItem, RedditPostsAdapter.MyViewHolder>(
    DiffUtilCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val scoreText = itemView.score
        val commentsText = itemView.comments
        val titleText = itemView.title

        fun bindPost(redditPost: UserItem) {
            scoreText.text = redditPost.login
            commentsText.text = redditPost.site_admin.toString()
            titleText.text = redditPost.gists_url
        }
    }
}