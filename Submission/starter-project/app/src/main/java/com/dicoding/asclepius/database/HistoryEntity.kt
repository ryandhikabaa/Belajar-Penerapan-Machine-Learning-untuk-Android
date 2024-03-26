package com.dicoding.asclepius.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "label_hasil")
    var label_hasil: String? = null,

    @ColumnInfo(name = "confidence_hasil")
    var confidence_hasil: String? = null
) : Parcelable
