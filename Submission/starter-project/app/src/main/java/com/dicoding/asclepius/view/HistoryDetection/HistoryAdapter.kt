package com.dicoding.asclepius.view.HistoryDetection

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.databinding.ItemRowHistoryBinding
import com.dicoding.asclepius.helper.HistoryDiffCallback
import com.dicoding.asclepius.utils.Config


class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback


    private val listHistory = ArrayList<HistoryEntity>()
    fun setListHistory(listHistory: List<HistoryEntity>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listHistory[position])
        }
    }

    class HistoryViewHolder(private val binding: ItemRowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyEntity: HistoryEntity) {
            with(binding){
                val uri = Uri.parse(historyEntity.image)
                Config.Constants.displayImageFromUri(binding.root.context, uri, ivDetection)
                if(historyEntity.label_hasil.equals("Cancer")){
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_red)
                    resultText.text = "${historyEntity.confidence_hasil} " + historyEntity.label_hasil
                    resultText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorRed))
                }else {
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_green)
                    resultText.text = "${historyEntity.confidence_hasil} " + historyEntity.label_hasil
                    resultText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorGreen))
                }
                tvDate.text = historyEntity.date
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: HistoryEntity)
    }
}