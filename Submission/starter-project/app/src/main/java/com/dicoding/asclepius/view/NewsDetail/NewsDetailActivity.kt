package com.dicoding.asclepius.view.NewsDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityNewsDetailBinding
import com.dicoding.asclepius.utils.Config


class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val news: ArticlesItem? = intent.getParcelableExtra("NEWS_CLICKED")
            if (news != null) {
                ivBack.setOnClickListener(View.OnClickListener { finish() })
                Glide.with(root)
                    .load(news.urlToImage)
                    .into(ivNews)
                tvTitle.text = news.title
                tvPublished.text = news.publishedAt?.substring(0, 10)
                tvDesc.text = news.description
                tvContent.text = news.content

                btShow.setOnClickListener(View.OnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(news.url))
                    startActivity(i)
                })

                btShare.setOnClickListener(View.OnClickListener {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, news.url)
                    startActivity(Intent.createChooser(shareIntent, "Bagikan link melalui"))
                })
            }else {
                Toast.makeText(this@NewsDetailActivity, Config.Constants.EROR_JARINGAN_ON_ERROR, Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }
}