package com.dicoding.asclepius.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.asclepius.database.HistoryEntity

class HistoryDiffCallback (private val oldNoteList: List<HistoryEntity>, private val newNoteList: List<HistoryEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].date == newNoteList[newItemPosition].date
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.date == newNote.date && oldNote.image == newNote.image && oldNote.label_hasil == newNote.label_hasil && oldNote.confidence_hasil == newNote.confidence_hasil
    }
}