package com.example.customlauncher.appsdrawer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.customlauncher.R
import com.example.customlayoutlibrary.AppInfo
import com.example.customlayoutlibrary.AppList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_apps_drawer.*


class AppsDrawerActivity : AppCompatActivity(), AppClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AppsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)
        Picasso.get().load(R.drawable.bg_teal).into(backgroundIV)
        adapter = AppsAdapter(this, AppList.getAppList(this), this)
        initRecyclerView(adapter)
        initSearchView(adapter)
    }

    private fun initSearchView(adapter: AppsAdapter) {
        app_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initRecyclerView(adapter: AppsAdapter) {
        linearLayoutManager = LinearLayoutManager(this)
        val layoutManager =
            StaggeredGridLayoutManager(4, 1)// this can be dynamic wrt the screen size
        recycler.layoutManager = layoutManager
        recycler.hasFixedSize()
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onAppClick(position: Int, appList: ArrayList<AppInfo>) {
        val launchIntent: Intent? = packageManager
            .getLaunchIntentForPackage(appList[position].packageName)
        startActivity(launchIntent)
        Toast.makeText(this, appList[position].appName, Toast.LENGTH_LONG)
            .show()
    }

    override fun onAppLongClick(position: Int, appList: ArrayList<AppInfo>) {
        val i = Intent()
        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:" + appList[position].packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        adapter = AppsAdapter(this, AppList.getAppList(this), this)
        initRecyclerView(adapter)
        initSearchView(adapter)
    }


}