package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import android.Manifest.permission
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    lateinit var textVievForCalculateButton : TextView
    lateinit var calcButton : Button
    lateinit var zeroButton : Button
    lateinit var imageView : ImageView

    fun printShit(): String {
        return (0..100).random().toString()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(permission.READ_EXTERNAL_STORAGE), 0)

        textVievForCalculateButton = findViewById(R.id.forCalcButton)
        textVievForCalculateButton.text = printShit()


        val clickListener = View.OnClickListener {
            when (it){
                calcButton -> {
                    textVievForCalculateButton.text = printShit()
                }
                zeroButton -> {
                    textVievForCalculateButton.text = "0"
                    Toast.makeText(this, "Zero Button has been pressed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        calcButton = findViewById(R.id.calc)
        calcButton.setOnClickListener(clickListener)

        zeroButton = findViewById(R.id.zero)
        zeroButton.setOnClickListener(clickListener)

        imageView = findViewById(R.id.imageView)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*if (ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show()
        }*/
        val rootFolder = Environment.getRootDirectory()
        val filesList = getListFiles(File("storage/emulated/0/DCIM/Camera"))


        if(filesList.first().exists()) {
            //Toast.makeText(this, filesList.first().absolutePath.toUri().toString(), Toast.LENGTH_LONG).show()
            val file = BitmapFactory.decodeFile(filesList.first().absolutePath)
            imageView.setImageURI(filesList.first().absolutePath.toUri())
        }
    }

    fun getListFiles(parentDir: File): List<File> {
        val inFiles = ArrayList<File>()
        val files = parentDir.listFiles()
        if(files != null) {
            for (file in files) {
                if (file.isFile) {
                    inFiles.add(file)
                }
            }
        }
        return inFiles
    }
}
