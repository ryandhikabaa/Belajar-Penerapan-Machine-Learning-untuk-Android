package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.ResultDetection.ResultActivity
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            galleryButton.setOnClickListener {
                startGallery()
            }
            analyzeButton.setOnClickListener {
                currentImageUri?.let {
                    analyzeImage(it)
                } ?: run {
                    showToast(getString(R.string.empty_image_warning))
                }
            }

            btClearImage.setOnClickListener(View.OnClickListener {
                currentImageUri = null
                previewImageView.setImageResource(R.drawable.ic_place_holder)
                analyzeButton.isEnabled= false
                btClearImage.isEnabled = false
            })
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
            binding.analyzeButton.isEnabled= true
            binding.btClearImage.isEnabled= true
        }
    }

    private fun analyzeImage(uri: Uri) {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        binding.divLoading.visibility = View.VISIBLE
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            contentResolver = contentResolver,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }
                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { it ->
                            binding.divLoading.visibility = View.GONE
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                val sortedCategories =
                                    it[0]
                                val displayResult =
//                                    sortedCategories.joinToString("\n") {
//                                        "${it.label} " + NumberFormat.getPercentInstance()
//                                            .format(it.score).trim()
//                                    }

//                                showToast(it[0].categories[0].label)
//                                currentImageUri?.let { it1 -> moveToResult(it1,it[0].categories[0].label, NumberFormat.getPercentInstance().format(it[0].categories[0].score)) }
                                moveToResult(currentImageUri.toString(),it[0].categories[0].label, NumberFormat.getPercentInstance().format(it[0].categories[0].score))

//                                binding.tvResult.text = displayResult
//                                binding.tvInferenceTime.text = "$inferenceTime ms"
                            } else {
//                                binding.tvResult.text = ""
//                                binding.tvInferenceTime.text = ""
                            }
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(uri)

    }

    private fun moveToResult(image:String, label: String, confidence: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("DATA_URI", image)
        intent.putExtra("DATA_LABEL", label)
        intent.putExtra("DATA_CONFIDENCE", confidence)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}