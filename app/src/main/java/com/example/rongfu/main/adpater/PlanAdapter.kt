package com.example.rongfu.main.adpater

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rongfu.R
import com.example.rongfu.bean.Plan

class PlanAdapter(private val context: Context,private val list: MutableList<Plan>) :
    RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null

    fun setListner(listener: OnClickListener) {
        this.listener = listener
    }
    fun setData(list:List<Plan>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_title)
        val addTime = view.findViewById<TextView>(R.id.tv_addTime)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val btnFinished = view.findViewById<TextView>(R.id.tv_finished)

        @SuppressLint("SetTextI18n")
        fun setData(position: Int) {
            title.text = list[position].title
            addTime.text = "创建于${list[position].addTime}"
            progressBar.progress = list[position].progress.toInt()
            btnFinished.setOnClickListener {
                listener!!.onClick(list[position])
            }
        }
    }

    interface OnClickListener {
        fun onClick(plan: Plan)
    }
}