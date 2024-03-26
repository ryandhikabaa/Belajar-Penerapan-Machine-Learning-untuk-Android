package com.dicoding.asclepius.view.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityDashboardBinding
import com.dicoding.asclepius.view.Dashboard.adapter.NewsAdapter
import com.dicoding.asclepius.view.HistoryDetection.HistoryActivity
import com.dicoding.asclepius.view.MainActivity
import com.dicoding.asclepius.view.NewsDetail.NewsDetailActivity
import com.dicoding.asclepius.view.ViewModelFactory.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardViewModel = obtainViewModel(this@DashboardActivity)

        dashboardViewModel.listItem.observe(this) { newsList ->
            setNewsData(newsList)
        }

        dashboardViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        dashboardViewModel.snackbarText.observe(this) {

            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        with(binding){
            val layoutManager = LinearLayoutManager(this@DashboardActivity)
            binding.rvArticle.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@DashboardActivity, layoutManager.orientation)
            binding.rvArticle.addItemDecoration(itemDecoration)

            btDetectionHere.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@DashboardActivity, MainActivity::class.java)
                startActivity(intent)
            })

            var appVersion = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName

            tvAppVersion.text = "Apps Ver. : $appVersion"

            fabHistory.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@DashboardActivity, HistoryActivity::class.java)
                startActivity(intent)
            })
        }
    }

    private fun setNewsData(usersItem: List<ArticlesItem>) {
        val adapter = NewsAdapter()
        adapter.submitList(usersItem)
        binding.rvArticle.adapter = adapter
        adapter.setOnItemClickCallback(object : NewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ArticlesItem) {
                val intent = Intent(this@DashboardActivity, NewsDetailActivity::class.java)
                intent.putExtra("NEWS_CLICKED", data)
                startActivity(intent)
            }
        })
    }


    private fun showLoading(state: Boolean) {
        binding.divLoading.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvArticle.visibility = if (state) View.GONE else View.VISIBLE

    }

    private fun obtainViewModel(activity: AppCompatActivity): DashboardViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory).get(DashboardViewModel::class.java)
    }

}