package com.dicoding.asclepius.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(historyEntity: HistoryEntity)

    @Query("SELECT * from HistoryEntity ORDER BY date ASC")
    fun getAllHistory(): LiveData<List<HistoryEntity>>

    // Menghapus semua item
    @Query("DELETE FROM HistoryEntity")
    fun deleteAll()
}