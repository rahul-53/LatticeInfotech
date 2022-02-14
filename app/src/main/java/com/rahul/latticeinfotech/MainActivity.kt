package com.rahul.latticeinfotech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.latticeinfotech.adapter.NewsAdapter
import com.rahul.latticeinfotech.api.RetrofitNews
import com.rahul.latticeinfotech.model.Article
import com.rahul.latticeinfotech.model.ArticleResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum =1
    var totalArticles = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //adapter
        adapter = NewsAdapter(this@MainActivity, articles)
        rvNews.adapter = adapter

        //layout manager
        val layoutManager = LinearLayoutManager(this)


        if (totalArticles>layoutManager.itemCount){
            pageNum++
            getNews()
        }

        rvNews.layoutManager = layoutManager
         getNews()
    }

    private fun getNews() {
        Log.d("pager","page number $pageNum")
        val news = RetrofitNews.apiService.getHeadlines("in", pageNum)
        news.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                val news:ArticleResponse? = response.body()
                if (news!= null){
                    totalArticles= news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                   /* adapter = NewsAdapter(this@MainActivity, news.articles)
                    rvNews.adapter = adapter
                    rvNews.layoutManager = LinearLayoutManager(this@MainActivity)*/
                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.d("rohit","error")
            }
        })
    }

}


