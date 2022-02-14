package com.rahul.latticeinfotech.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.latticeinfotech.R
import com.rahul.latticeinfotech.model.Article
import kotlinx.android.synthetic.main.new_item_layout.view.*

class NewsAdapter(val context:Context, val articles:List<Article>):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.new_item_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.itemView.tvHeading.text =article.title
        holder.itemView.tvDescription.text = article.description
        holder.itemView.tvDate.text = article.publishedAt
        holder.itemView.tvSource.text = article.source.toString()
        Glide.with(context).load(article.urlToImage).into(holder.itemView.ivNewsImage)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}