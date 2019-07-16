package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import android.Manifest.permission
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList
import androidx.core.content.FileProvider

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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun clickImage(view: View){

//        val string = view.tag.toString()
//        val vtag = Uri.parse(string.substring(8))
//        val legalUri = vtag.pathSegments
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(vtag, "image/*")
//        startActivity(intent)

        val string = view.tag.toString() //file:///storage/emulated/0/DCIM/Camera/Selfies/IMG_20190430_121822.jpg
        val file = File("storage/emulated/0/DCIM/Camera/Selfies/IMG_20190430_121822.jpg")
        val data : Uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file)
        //this.grantUriPermission(this.packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        //val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(data, "image/*")
        //this.startActivity(intent)
    }

        class Adapter(private val values: List<Uri>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
                return ViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.imageButton!!.tag = values[position]
                Picasso.get().load(values[position]).fit().centerInside().into(holder.imageButton)
            }

            override fun getItemCount() = values.size

            class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
                var imageButton: ImageButton? = null

                init {
                    imageButton = itemView?.findViewById(R.id.image_button)
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
                    if(file.isFile && isMedia(file.extension) && !file.absolutePath.contains("cache") && !file.absolutePath.contains("thumbnails"))
                        outputArray.add(file.toUri())
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
