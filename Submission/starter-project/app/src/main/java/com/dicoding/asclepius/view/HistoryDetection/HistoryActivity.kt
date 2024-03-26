package com.dicoding.asclepius.view.HistoryDetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.view.HistoryDetection.HistoryDetail.HistoryDetailActivity
import com.dicoding.asclepius.view.ViewModelFactory.ViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.navigationIcon?.setTint(resources.getColor(android.R.color.white))

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            adapter = HistoryAdapter()
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.setHasFixedSize(true)
            rvHistory.adapter = adapter

            adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: HistoryEntity) {
                    val intent = Intent(this@HistoryActivity, HistoryDetailActivity::class.java)
                    intent.putExtra("DATA_CLICKED", data)
                    startActivity(intent)
                }
            })

            historyViewModel = obtainViewModel(this@HistoryActivity)

            historyViewModel.getAllHitory().observe(this@HistoryActivity) { historyList ->
                if (historyList != null) {
                    if (historyList.size > 0){
                        rvHistory.visibility = View.VISIBLE
                        tvHistoryKosong.visibility = View.GONE
                    }else{
                        rvHistory.visibility = View.GONE
                        tvHistoryKosong.visibility = View.VISIBLE
                    }
                    adapter.setListHistory(historyList)
                }
            }



        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_data -> {
                // Panggil metode untuk menghapus data di Room
//                deleteData()
                showDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Apakah Anda yakin ingin menghapus semua history?")
        alertDialogBuilder.setPositiveButton("Ya") { dialog, id ->
            // Panggil metode untuk menghapus item jika pengguna menekan "Ya"
            historyViewModel.deleteAll()
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialog, id ->
            // Tidak melakukan apa-apa jika pengguna menekan "Tidak"
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
    }
}