package com.example.myapplication

import android.net.Uri
import android.os.AsyncTask
import androidx.core.net.toUri
import java.io.File

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