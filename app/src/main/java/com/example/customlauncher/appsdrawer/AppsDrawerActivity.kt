package com.example.customlauncher.appsdrawer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.customlauncher.R
import com.example.customlayoutlibrary.AppInfo
import com.example.customlayoutlibrary.AppList
import kotlinx.android.synthetic.main.activity_apps_drawer.*


class AppsDrawerActivity : AppCompatActivity(), AppClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AppsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)
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
        val layoutManager = StaggeredGridLayoutManager(4, 1)// this can be dynamic wrt the screen size
        recycler.layoutManager = layoutManager
        recycler.hasFixedSize()
        recycler.adapter = adapter
    }

    override fun onAppClick(position: Int, appList: ArrayList<AppInfo>) {
        val launchIntent: Intent? = packageManager
            .getLaunchIntentForPackage(appList[position].packageName)
        startActivity(launchIntent)
        Toast.makeText(this, appList[position].appName, Toast.LENGTH_LONG)
            .show()
    }

    override fun onAppLongClick(position: Int, appList: ArrayList<AppInfo>) {
        Toast.makeText(this, appList[position].appName + " Long click", Toast.LENGTH_LONG)
            .show()
    }


}