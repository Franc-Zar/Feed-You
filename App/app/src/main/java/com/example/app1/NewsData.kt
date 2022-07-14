package com.example.app1

import tw.ktrssreader.kotlin.model.item.Guid

data class NewsData (var title: String, var desc: String, var link:String,
                     var category: String, var guid: Guid?){}
