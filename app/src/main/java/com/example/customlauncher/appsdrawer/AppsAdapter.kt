package com.example.customlauncher.appsdrawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.customlauncher.R
import com.example.customlayoutlibrary.AppInfo
import java.util.*
import kotlin.collections.ArrayList


class AppsAdapter(
    con: Context,
    val appsList: ArrayList<AppInfo>,
    private val appClickListener: AppClickListener
) : RecyclerView.Adapter<AppsAdapter.ViewHolder>(), Filterable {
    var appsFilterList = ArrayList<AppInfo>()

    init {
        appsFilterList = appsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsAdapter.ViewHolder {
        //This is what adds the code we've written in here to our target view
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppsAdapter.ViewHolder, pos: Int) {
        holder.bind(appsFilterList[holder.adapterPosition])

        holder.itemView.setOnClickListener {
            appClickListener.onAppClick(holder.adapterPosition, appsFilterList)
        }
        holder.itemView.setOnLongClickListener {
            appClickListener.onAppLongClick(holder.adapterPosition, appsFilterList)
            true
        }
    }

    override fun getItemCount(): Int {
        return appsFilterList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        var textView = itemView.findViewById(R.id.text) as TextView
        var img = itemView.findViewById(R.id.img) as ImageView

        fun bind(app: AppInfo) {
            val appLabel: String = app.appName
            val appPackage = app.packageName
            val appIcon = app.icon
            val textView: TextView = textView
            textView.text = appLabel
            val imageView: ImageView = img
            imageView.setImageDrawable(appIcon)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                //checks if we have typed a text in the SeachView
                //If there is not any text, will return all items.
                val searchQuery = query.toString()
                appsFilterList = if (searchQuery.isNotEmpty()) {
                    val resultList = ArrayList<AppInfo>()
                    for (app in appsList) {
                        if (app.appName.toLowerCase(Locale.ROOT).contains(
                                searchQuery.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(app)
                        }
                    }
                    resultList
                } else {
                    appsList
                }
                val filterResults = FilterResults()
                filterResults.values = appsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(query: CharSequence?, results: FilterResults?) {
                appsFilterList = results?.values as ArrayList<AppInfo>
                notifyDataSetChanged()
            }

        }
    }


}