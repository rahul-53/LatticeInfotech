package com.rahul.latticeinfotech

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.latticeinfotech.adapter.NewsAdapter
import com.rahul.latticeinfotech.api.RetrofitNews
import com.rahul.latticeinfotech.model.Article
import com.rahul.latticeinfotech.model.ArticleResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()

    var pageNum =1
    var totalArticles = -1

    private  var tempArticles= mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //adapter
        adapter = NewsAdapter(this@MainActivity, articles)
        rvNews.adapter = adapter

        //layout manager
        val layoutManager = LinearLayoutManager(this)

        //paging
        if (totalArticles>layoutManager.itemCount){
            pageNum++
            getNews()
        }
        rvNews.layoutManager = layoutManager
         getNews()
    }

    private fun getNews() {
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

                }
            }
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.d("Main Activity","Something went wrong")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        var item:MenuItem = menu!!.findItem(R.id.app_bar_search)

        var searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
               if(newText!!.isNotEmpty()){
                   tempArticles.clear()
                   var search = newText.toLowerCase(Locale.getDefault())

                   for (article in articles){
                      if (article.title.toLowerCase(Locale.getDefault()).contains(search)){
                          tempArticles.add(article)
                      }
                       rvNews.adapter!!.notifyDataSetChanged()
                   }
               }
                else{
                    tempArticles.clear()
                    tempArticles.addAll(articles)
                    rvNews.adapter!!.notifyDataSetChanged()
               }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)

    }

}


