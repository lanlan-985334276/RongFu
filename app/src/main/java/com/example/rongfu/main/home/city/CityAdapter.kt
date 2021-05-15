package com.example.rongfu.main.home.city

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rongfu.R
import com.example.rongfu.utils.SharedPrefsUtils

class CityAdapter(private val context: Context, private val list: MutableList<String>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private var listener: OnClickListener? = null
    fun setData(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        Log.i("City","setData ${list.size} ${this.list.size}")
        notifyDataSetChanged()
    }

    fun setListener(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView = itemView.findViewById<TextView>(R.id.tv_city)
        fun setData(position: Int) {
            cityTextView.text = list[position]
            itemView.setOnClickListener {
                SharedPrefsUtils.putString(context, "city", list[position].split("-")[0])
                listener!!.onClick()
            }
        }
    }

    interface OnClickListener {
        fun onClick()
    }
}