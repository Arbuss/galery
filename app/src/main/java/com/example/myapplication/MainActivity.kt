package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import android.Manifest.permission
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var filesList: ArrayList<Uri>
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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val lit = LoadImgsTask()
        lit.execute(File("/storage/emulated/0").listFiles())
        val filesList = lit.get()

        recyclerView.adapter = Adapter(filesList)
    }

    fun isMedia(extension : String) : Boolean {
        return extension == "jpg" || extension == "png" || extension == "gif" || extension == "jpeg"
            || extension == "avi" || extension == "mkv" || extension == "mp4"
            || extension == "mp3"
    }

    suspend fun recursiveWalk(files : Array<File>){
        if(files == null) return
        for (file in files) {
            if(file.isFile && isMedia(file.extension))
                GlobalScope.launch { filesList.add(file.toUri()) }
            if(file.isDirectory) {
                recursiveWalk(file.listFiles())
            }
        }
    }

    fun getListFiles(parentDir: File) {
        GlobalScope.async {
            val files = parentDir.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        filesList.add(file.toUri())
                    }
                }
            }
        }
    }

    class Adapter(private val values: List<Uri>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            //Picasso.get().load(values[position]).into(holder.imageView1)
            Picasso.get().load(values[position]).fit().centerCrop().into(holder.imageView1)
            //Picasso.get().load(values[position]).resize(130, 130).into(holder.imageView1)
            //holder.imageView1!!.setImageURI(values[position])

        }

        override fun getItemCount() = values.size

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            var imageView1: ImageView? = null

            init {
                imageView1 = itemView?.findViewById(R.id.imageView1)
            }
        }
    }

    class LoadImgsTask : AsyncTask<Array<File>, Array<Uri>, ArrayList<Uri>>() {

        fun isMedia(extension : String) : Boolean {
            return extension == "jpg" || extension == "png" || extension == "gif" || extension == "jpeg"
                    || extension == "avi" || extension == "mkv" || extension == "mp4"
                    || extension == "mp3"
        }

        fun recursiveWalk(files : Array<File>, outputArray : ArrayList<Uri>){
            for (file in files) {
                if(file.isFile && isMedia(file.extension))
                    GlobalScope.launch { outputArray.add(file.toUri()) }
                if(file.isDirectory) {
                    recursiveWalk(file.listFiles(), outputArray)
                }
            }
        }

        override fun doInBackground(vararg files: Array<File>): ArrayList<Uri>? {
            val list: ArrayList<Uri> = ArrayList()
            recursiveWalk(files[0], list)
            return list
        }

        override fun onPostExecute(result: ArrayList<Uri>?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: Array<Uri>?) {
            super.onProgressUpdate(*values)
        }
    }
}
