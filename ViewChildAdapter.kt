package com.androidstackoverflow.kotlintestview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kotlinx.android.synthetic.main.child_recycler.view.*


class ViewChildAdapter(private val children:List<ModelChild>):RecyclerView.Adapter<ViewChildAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_recycler,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return children.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.textView.text = child.item
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.child_textView
        var child_textView:TextView =itemView.child_textView
    }
}