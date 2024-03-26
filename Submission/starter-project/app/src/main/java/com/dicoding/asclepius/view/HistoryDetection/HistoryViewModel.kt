package com.dicoding.asclepius.view.HistoryDetection

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.database.Repository.HistoryRepository

class HistoryViewModel (application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun getAllHitory(): LiveData<List<HistoryEntity>> = mHistoryRepository.getAllHistory()

    fun deleteAll() = mHistoryRepository.deleteAll()

}