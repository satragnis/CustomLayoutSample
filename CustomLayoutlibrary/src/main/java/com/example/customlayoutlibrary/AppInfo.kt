package com.example.customlayoutlibrary

import android.graphics.drawable.Drawable

data class AppInfo(
    val appName:String,
    val packageName:String,
    val icon:Drawable,
    val mainActivityClassName: String?,
    val versionCode:Long,
    val versionName:String
)
