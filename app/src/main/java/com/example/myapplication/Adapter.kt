package com.example.myapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter(private val values: List<Uri>, var callback: ButtonCallback) : RecyclerView.Adapter<Adapter.Holder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return Holder(callback, itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(values[position])
    }

    inner class Holder(var callback: ButtonCallback, var view : View) : RecyclerView.ViewHolder(view){
        var imageButton = view.findViewById<ImageButton>(R.id.image_button)
        fun bind(uri : Uri){
            Picasso.get().load(uri).fit().centerInside().into(imageButton)
            imageButton.setOnClickListener {
                callback.onOpen(view.context, uri)
            }
        }
    }
}