package com.example.myapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

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