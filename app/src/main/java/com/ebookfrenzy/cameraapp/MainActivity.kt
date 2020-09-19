package com.ebookfrenzy.cameraapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


private const val REQUEST_CODE = 1
private  lateinit var myPhoto:File
private var myPhotoName = "photo"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnTakePicture.setOnClickListener {

            val intentPics = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            myPhoto = getPhotoFromCamera(myPhotoName)
            //security part
            val newProvider = FileProvider.getUriForFile(this,"com.ebookfrenzy.cameraapp.fileprovider", myPhoto)
            intentPics.putExtra(MediaStore.EXTRA_OUTPUT,newProvider)

            if (intentPics.resolveActivity(this.packageManager) != null) {
                startActivityForResult(intentPics, REQUEST_CODE)

            } else {
                //there is an error
                Toast.makeText(this, "No Camera", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getPhotoFromCamera(fileName: String): File {
      val localDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",localDirectory)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //val LQimg = data?.extras?.get("data") as Bitmap
            val HQImg = BitmapFactory.decodeFile(myPhoto.absolutePath)
            imageView.setImageBitmap(HQImg)


            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}