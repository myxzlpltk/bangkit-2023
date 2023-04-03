package com.dicoding.storyapp.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.dicoding.storyapp.utils.Configuration.DIM_IMAGE
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

val timeStamp: String
    get() = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    return File.createTempFile(timeStamp, ".jpg", context.cacheDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

/*fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}*/

fun prepareImage(file: File): File {
    // Decode File into Bitmap
    var bitmap = BitmapFactory.decodeFile(file.path)
    // Fix rotation
    bitmap = rotateBitmap(bitmap, file.path)
    // Determine the size of the smallest edge
    val size = bitmap.width.coerceAtMost(bitmap.height)
    // Calculate the coordinates of the top-left corner of the square
    val x = (bitmap.width - size) / 2
    val y = (bitmap.height - size) / 2
    // Crop the bitmap to the square size and position
    bitmap = Bitmap.createBitmap(bitmap, x, y, size, size)
    // Resize the bitmap into DIM_SIZE (1080)
    bitmap = Bitmap.createScaledBitmap(bitmap, DIM_IMAGE, DIM_IMAGE, true)
    // Compress and save bitmap into file
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
    // Return it's own file
    return file
}

fun rotateBitmap(bitmap: Bitmap, path: String): Bitmap {
    val exif = ExifInterface(path)
    val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
    val matrix = Matrix()
    when (orientation) {
        6 -> matrix.postRotate(90f)
        3 -> matrix.postRotate(180f)
        8 -> matrix.postRotate(270f)
    }

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024