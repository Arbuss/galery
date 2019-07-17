package com.example.myapplication

import android.content.Context
import android.net.Uri

interface ButtonCallback {
    fun onOpen(context: Context, uri : Uri)
}