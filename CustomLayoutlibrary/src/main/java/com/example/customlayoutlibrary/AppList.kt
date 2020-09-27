package com.example.customlayoutlibrary

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

object AppList {
        fun getAppList(c: Context): ArrayList<AppInfo> {
            val appsList = ArrayList<AppInfo>()
            val pm: PackageManager = c.packageManager
            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val allApps: List<ResolveInfo> = pm.queryIntentActivities(i, 0)
            appsList.clear()
            for (app in allApps) {
                val versionCode: Long =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        pm.getPackageInfo(app.activityInfo.packageName, 0).longVersionCode
                    } else {
                        pm.getPackageInfo(app.activityInfo.packageName, 0).versionCode.toLong()
                    }
                val appInfo = AppInfo(
                    app.loadLabel(pm).toString(), app.activityInfo.packageName,
                    app.activityInfo.loadIcon(pm),
                    app.activityInfo.parentActivityName,
                    versionCode,
                    pm.getPackageInfo(app.activityInfo.packageName, 0).versionName
                )
                appsList.sortBy { it.appName}
                appsList.add(appInfo)
            }
            return appsList
        }
}