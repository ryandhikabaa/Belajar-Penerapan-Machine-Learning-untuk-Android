package com.dicoding.asclepius.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Config {
    object Constants {
        const val EROR_JARINGAN_ON_ERROR = "Opps! Mohon maaf terjadi kesalahan sistem, silahkan ulangi beberapa saat kembali "
        const val OPPS = "Opps!, Respon Server Gagal, silahkan ulangi beberapa saat kembali"

        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }

        // Method untuk menampilkan gambar ke ImageView dari URI
        fun displayImageFromUri(
            @NonNull context: Context,
            @Nullable imageUri: Uri?,
            @NonNull imageView: ImageView
        ) {
            if (imageUri == null) return
            try {
                val parcelFileDescriptor = context.contentResolver.openFileDescriptor(imageUri, "r")
                if (parcelFileDescriptor != null) {
                    val fileDescriptor = parcelFileDescriptor.fileDescriptor
                    val imageBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
                    parcelFileDescriptor.close()
                    imageView.setImageBitmap(imageBitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}