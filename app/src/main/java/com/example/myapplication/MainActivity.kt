package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.annotation.RequiresApi
import java.io.File
import android.Manifest.permission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    lateinit var imageViewsList : List<ImageView>
    lateinit var filesList : ArrayList<File>
    lateinit var recyclerView : RecyclerView

    fun printShit(): String {
        return (0..100).random().toString()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(permission.READ_EXTERNAL_STORAGE), 0)
        filesList = ArrayList()

        imageViewsList = listOf(
            findViewById(R.id.imageView),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4),
            findViewById(R.id.imageView5),
            findViewById(R.id.imageView6)
        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(imageViewsList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*if (ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show()
        }*/
        val rootFolder = Environment.getRootDirectory()
        getListFiles(File("storage/emulated/0/DCIM/Camera"))

        if(filesList.isNotEmpty()) {
            var i = 0
            for(file: File in filesList){
                if(i == 6)
                    i = 0
                imageViewsList[i].setImageURI(file.toUri())
                i++
            }
        }
    }

    fun getListFiles(parentDir: File) {
        val files = parentDir.listFiles()
        if(files != null) {
            for (file in files) {
                if (file.isFile) {
                    filesList.add(file)
                }
            }
        }
    }
}

class Adapter(private val values: List<ImageView>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView = values[position]
    }

    override fun getItemCount() = values.size

class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
    var textView: ImageView? = null
    init{
        textView = itemView?.findViewById(R.id.imageView7)
    }
}
}