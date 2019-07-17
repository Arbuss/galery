package com.example.myapplication

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

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

        recyclerView.adapter = Adapter(filesList, object:ButtonCallback {
            override fun onOpen(context: Context, uri: Uri) {
                val data : Uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, uri.toFile())
                context.grantUriPermission(context.packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(data, "image/*")
                context.startActivity(intent)
            }
        })
    }
}
