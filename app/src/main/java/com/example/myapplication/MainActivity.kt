package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var filesList: ArrayList<File>
    lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(permission.READ_EXTERNAL_STORAGE), 0)
        filesList = ArrayList()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //getListFiles(File("storage/emulated/0/DCIM/Camera"))
        recyclerView.adapter = Adapter(filesList)

        //val photoPickerIntent = Intent(Intent.ACTION_PICK)
        //photoPickerIntent.type = "image/*"
        //startActivityForResult(photoPickerIntent, 1)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)

        //val bitmap : Bitmap = findViewById(R.id.imageView1)
        val shittyImageView = findViewById<ImageView>(R.id.imageView1)
        when (requestCode){
            1 -> {
                if(resultCode == RESULT_OK){
                    val selectedImage = imageReturnedIntent?.data
                    shittyImageView.setImageURI(selectedImage)
                    //bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    //shittyImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, selectedImage))
                }
            }
        }
    }*/

    fun getListFiles(parentDir: File) {
        val files = parentDir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isFile) {
                    filesList.add(file)
                }
            }
        }
    }

    class Adapter(private val values: List<File>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.imageView1?.setImageURI(values[position].toUri())
        }

        override fun getItemCount() = values.size

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            var imageView1: ImageView? = null

            init {
                imageView1 = itemView?.findViewById(R.id.imageView1)
            }
        }
    }
}