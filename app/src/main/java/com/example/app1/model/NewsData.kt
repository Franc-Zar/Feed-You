package com.example.app1.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import tw.ktrssreader.kotlin.model.item.Guid

data class NewsData(
    var title: String, var desc: String, var link:String,
    var category: Int, var guid: Guid?, val icon: Bitmap?){}
//TODO il logo e la categoria si possono salvare una sola volta per feed, quindi eventualmente pensare ad un model relativo il feed

