package com.dicoding.asclepius.view.HistoryDetection.HistoryDetail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.databinding.ActivityHistoryDetailBinding
import com.dicoding.asclepius.utils.Config

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            val data: HistoryEntity? = intent.getParcelableExtra("DATA_CLICKED")
            if (data != null){
                val uri = Uri.parse(data.image)
                Config.Constants.displayImageFromUri(binding.root.context, uri, resultImage)

                val layoutInflater = LayoutInflater.from(this@HistoryDetailActivity)
                val rootView = findViewById<LinearLayout>(R.id.divMasukan)
                rootView.removeAllViews()

                if(data.label_hasil.equals("Cancer")){
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_red)
                    resultText.text = "${data.confidence_hasil} " + data.label_hasil
                    resultText.setTextColor(ContextCompat.getColor(this@HistoryDetailActivity, R.color.colorRed))
                    val includedLayout = layoutInflater.inflate(R.layout.tips_pengobatan_kanker, null)
                    rootView.addView(includedLayout)
                }else {
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_green)
                    resultText.text = "${data.confidence_hasil} " + data.label_hasil
                    resultText.setTextColor(ContextCompat.getColor(this@HistoryDetailActivity, R.color.colorGreen))
                        val includedLayout = layoutInflater.inflate(R.layout.tips_menghindari_kanker, null)
                        rootView.addView(includedLayout)
                }

                tvDate.text = getString(R.string.history_date) + " ${data.date}"


            }

        }
    }
}