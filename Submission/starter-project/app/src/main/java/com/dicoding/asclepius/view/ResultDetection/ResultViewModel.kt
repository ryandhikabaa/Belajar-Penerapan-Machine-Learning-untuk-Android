package com.dicoding.asclepius.view.ResultDetection

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.database.Repository.HistoryRepository

class ResultViewModel  (application: Application) : ViewModel()  {

    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    companion object{
        private const val TAG = "ResultViewModel"
    }

    fun insert(historyEntity: HistoryEntity) {
        mHistoryRepository.insert(historyEntity)
    }
}