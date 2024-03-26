package com.dicoding.asclepius.database.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.database.HistoryDao
import com.dicoding.asclepius.database.HistoryDatabase
import com.dicoding.asclepius.database.HistoryEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application)  {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryDatabase.getDatabase(application)
        mHistoryDao = db.userFavDao()
    }

    fun getAllHistory(): LiveData<List<HistoryEntity>> = mHistoryDao.getAllHistory()

    fun insert(historyEntity: HistoryEntity) {
        executorService.execute { mHistoryDao.insert(historyEntity) }
    }

}