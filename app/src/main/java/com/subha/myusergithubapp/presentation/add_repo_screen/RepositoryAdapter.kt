package com.subha.myusergithubapp.presentation.add_repo_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.data.models.UserRepoResItem
import com.subha.myusergithubapp.databinding.ItemRepositoriesBinding


class RepositoryAdapter(context: Context, val noti: clickListener) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var mData = ArrayList<UserRepoResItem>()
    var context = context
    var notify = noti


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var bind = ItemRepositoriesBinding.bind(itemView)

        fun update(
            get: UserRepoResItem,
            context: Context,
            position: Int,
            noti: clickListener
        ) {
            bind.tvName.text = get.name
            bind.tvDescription.text = get.description
            bind.llShare.setOnClickListener {
                noti.share(get.html_url)
            }
            bind.root.setOnClickListener {
                noti.enter(get.html_url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View

        v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repositories, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(mData[position], context, position, notify)
    }

    // return the size of languageList
    override fun getItemCount(): Int = mData.size


    fun setData(list: ArrayList<UserRepoResItem>) {
        mData = list
        notifyDataSetChanged()
    }

    interface clickListener {
        fun enter(
            name: String
        )

        fun share(
            name: String
        )
    }

}