package com.dicoding.asclepius.view.Dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.response.ArticlesItem
import com.dicoding.asclepius.data.response.NewsResponse
import com.dicoding.asclepius.data.retrofit.ApiConfig
import com.dicoding.asclepius.utils.Config
import com.dicoding.asclepius.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(application: Application) : ViewModel() {

    private val _listItem = MutableLiveData<List<ArticlesItem>>()
    val listItem: LiveData<List<ArticlesItem>> = _listItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "DashboardViewModel"
    }


    init {
        fetchNews()
    }

    private fun fetchNews(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val articles = response.body()?.articles

                        articles?.let {
                            val filteredArticles = it.filter { article ->
//                                Cek karena ada data dari servere yang sudah di remove,
//                                secara ui dan ux apabila tetap ditampilkan kurang pas, maka tidak usah di add ke list
                                article?.title != "[Removed]"
                                article?.description != null
                                article?.content != null
                                article?.urlToImage != null
                            }
                            _listItem.value = filteredArticles
                        }
                    }
                } else {
                    Log.e(TAG, "onFailure respon: ${response}")
                    _snackbarText.value = Event(Config.Constants.OPPS + " ${response}")
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure error: ${t.message}")
                _snackbarText.value = Event(Config.Constants.EROR_JARINGAN_ON_ERROR)
            }
        })
    }

}