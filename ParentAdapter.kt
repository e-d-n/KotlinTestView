package com.androidstackoverflow.kotlintestview

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView


class ParentAdapter(parentList: List<ModelParent>,internal var context: Context):RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var parentList: List<ModelParent> = ArrayList()
    init { this.parentList = parentList }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_parent,parent,false)
        return ParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val items = parentList[position]
        holder.item.text = items.dept


        holder.editCLICK.setOnClickListener {
            val i = Intent(context, EnterParentActivity::class.java)
            i.putExtra("FROM", "U")
            i.putExtra("MainActId",items.idD)
            i.putExtra("PFK",items.fkD)
            i.putExtra("ET",items.dept)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }


    inner class ParentViewHolder(view: View):RecyclerView.ViewHolder(view){
        var item: TextView = view.findViewById(R.id.tvDept) as TextView
        var editCLICK: RelativeLayout = view.findViewById(R.id.editCLICK) as RelativeLayout
    }

}