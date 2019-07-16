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
        val data : Uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, File(view.tag.toString().substring(8)))
        this.grantUriPermission(this.packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(data, "image/*")
        this.startActivity(intent)
    }




}
