package com.zszuev.calendarplanner.domain.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ProfilePhotoHelper @Inject constructor(){

    fun loadProfileImage(context: Context, view: ImageView) {
        val filename = "profile_image.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, filename)

        if (imageFile.exists()) {
            val imageUri = Uri.fromFile(imageFile)
            view.setImageURI(imageUri)
        }
    }

    fun saveImageToStorage(context: Context, bitmap: Bitmap): Uri? {
        val filename = "profile_image.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, filename)
        try {
            FileOutputStream(imageFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                return Uri.fromFile(imageFile)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}