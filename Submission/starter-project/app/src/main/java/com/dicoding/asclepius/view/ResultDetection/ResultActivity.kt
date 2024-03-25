package com.dicoding.asclepius.view.ResultDetection

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                if(label.equals("Cancer")){
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_red)
                    resultText.text = label
                    resultText.setTextColor(ContextCompat.getColor(this@ResultActivity, R.color.colorRed))
                }else {
                    divLabel.setBackgroundResource(R.drawable.rectangle_stroke_green)
                    resultText.text = label
                    resultText.setTextColor(ContextCompat.getColor(this@ResultActivity, R.color.colorGreen))
                }
                confidenceScore.text = getString(R.string.confidence) + " $confidence"
            } else {
                finish() // Tutup ActivityB
            }

        }
    }


}