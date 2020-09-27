package com.example.customlauncher.appsdrawer

import com.example.customlayoutlibrary.AppInfo
import com.example.customlayoutlibrary.AppList

interface AppClickListener {
    fun onAppClick(position:Int,appList: ArrayList<AppInfo>)
    fun onAppLongClick(position:Int,appList: ArrayList<AppInfo>)
}