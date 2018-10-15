package com.androidstackoverflow.kotlintestview

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView


class ChildAdapter(childList: List<ModelChild>, internal var context: Context):RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    private var childList: List<ModelChild> = ArrayList()
    init { this.childList = childList }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.card_child,parent,false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        var items = childList[position]
        holder.item.text = items.item


        holder.editCLICK.setOnClickListener {
            val i = Intent(context, EnterChildActivity::class.java)
            i.putExtra("FROM", "U")
            i.putExtra("MainActId",items.idI)
            i.putExtra("CFK",items.fkI)
            i.putExtra("ET",items.item)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    inner class ChildViewHolder(view: View):RecyclerView.ViewHolder(view){
        var item: TextView = view.findViewById(R.id.tvItem) as TextView
        var editCLICK: RelativeLayout = view.findViewById(R.id.editCLICK) as RelativeLayout
    }
}
