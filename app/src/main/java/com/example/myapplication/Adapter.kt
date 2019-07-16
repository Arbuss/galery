package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter(private val values: List<Uri>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageButton!!.tag = values[position]
        Picasso.get().load(values[position]).fit().centerInside().into(holder.imageButton)

        holder.imageButton!!.setOnClickListener {
            val context = it.context
            val data : Uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, values[position].toFile())
            context.grantUriPermission(context.packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(data, "image/*")
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var imageButton: ImageButton? = null

        init {
            imageButton = itemView?.findViewById(R.id.image_button)
        }
    }
}