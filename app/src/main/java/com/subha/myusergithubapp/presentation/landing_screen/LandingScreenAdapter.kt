package com.subha.myusergithubapp.presentation.landing_screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.data.models.Item
import com.subha.myusergithubapp.databinding.UserItemBinding


class LandingScreenAdapter(context: Context, val noti: clickListener) :
    RecyclerView.Adapter<LandingScreenAdapter.ViewHolder>() {

    private var mData = ArrayList<Item>()
    var context = context
    var notify = noti


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var bind = UserItemBinding.bind(itemView)

        fun update(
            get: Item,
            context: Context,
            position: Int,
            noti: clickListener
        ) {

            Glide.with(context)
                .load(get.avatar_url) // image url
                .fitCenter()
                .into(bind.ivPic)

            bind.tvName.text = get.login
            bind.root.setOnClickListener {
                noti.enter(get.login)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View

        v = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(mData[position], context, position, notify)
    }

    // return the size of languageList
    override fun getItemCount(): Int = mData.size


    fun setData(list: ArrayList<Item>) {
        mData = list
        notifyDataSetChanged()
    }

    interface clickListener {
        fun enter(
            name: String
        )
    }

}