package com.dicoding.asclepius.view.ResultDetection

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.utils.Config
import com.dicoding.asclepius.view.Dashboard.DashboardViewModel
import com.dicoding.asclepius.view.ViewModelFactory.ViewModelFactory

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultViewModel = obtainViewModel(this@ResultActivity)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            val image = intent.getStringExtra("DATA_URI")
            val label = intent.getStringExtra("DATA_LABEL")
            val confidence = intent.getStringExtra("DATA_CONFIDENCE")
            if (!image.isNullOrEmpty()) {
                resultImage.setImageURI(Uri.parse(image))

                val layoutInflater = LayoutInflater.from(this@ResultActivity)
                val rootView = findViewById<LinearLayout>(R.id.divMasukan)
                rootView.removeAllViews()

                if(label.equals("Cancer")){
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_red)
                    resultText.text = "${confidence} " + label
                    resultText.setTextColor(ContextCompat.getColor(this@ResultActivity, R.color.colorRed))
                    val includedLayout = layoutInflater.inflate(R.layout.tips_pengobatan_kanker, null)
                    rootView.addView(includedLayout)
                }else {
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_green)
                    resultText.text = "${confidence} " + label
                    resultText.setTextColor(ContextCompat.getColor(this@ResultActivity, R.color.colorGreen))
                    val includedLayout = layoutInflater.inflate(R.layout.tips_menghindari_kanker, null)
                    rootView.addView(includedLayout)
                }

                // save to history
                val historyEntity = HistoryEntity(
                    date = Config.Constants.getCurrentDate(),
                    image = image,
                    label_hasil = label,
                    confidence_hasil = confidence
                )
                resultViewModel.insert(historyEntity)

            } else {
                finish() // Tutup ActivityB
            }

        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory).get(ResultViewModel::class.java)
    }


}