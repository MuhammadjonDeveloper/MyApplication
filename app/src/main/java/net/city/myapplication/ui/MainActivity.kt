package net.city.myapplication.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import net.city.myapplication.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


const val REQUEST_IMAGE_CAPTURE = 12

class MainActivity : AppCompatActivity() {
    lateinit var currentPhotoPath: String

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photofile: File? = null
            try {
//                photofile = createImageFile()
            } catch (ix: IOException) {
            }
            if (photofile != null) {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,  /*"com.android.fileprovider"*/
                    "com.example.android.fileprovider"
                    , photofile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(
                    takePictureIntent,
                    REQUEST_IMAGE_CAPTURE
                )
                //                setPic();
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeSmap: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeSmap + "_"
        val storageDir: Array<File> = getExternalFilesDirs(Environment.DIRECTORY_PICTURES)
        val image: File = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir[0]
        )
        currentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic()
        }
    }

    private fun setPic() {
        // Get the dimensions of the View
//        val targetW: Int = image_View.width
//        val targetH: Int = image_View.height
        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight
        // Determine how much to scale down the image
//        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
//        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
//        image_View.setImageURI(Uri.parse(currentPhotoPath))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->
                true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
