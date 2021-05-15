package com.example.rongfu.main.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rongfu.R
import com.example.rongfu.bean.StepBean

class StepAdpter(val context: Context, val list: List<StepBean>) :
    RecyclerView.Adapter<StepAdpter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_step, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val id = view.findViewById<TextView>(R.id.tv_id)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val step = view.findViewById<TextView>(R.id.tv_step)
        fun setData(position: Int) {
            id.text = "${position + 1}"
            name.text = "${list[position].userName}"
            step.text = "${list[position].num}"
        }
    }
}