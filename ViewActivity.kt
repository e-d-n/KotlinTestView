package com.androidstackoverflow.kotlintestview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_view.*
import java.util.ArrayList

class ViewActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    private var RecyclerAdapter1: ViewAdapter? = null
    private var RecyclerAdapter2: ViewChildAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null

    private val db = DBHelper(this)

    private var parentList:List<ModelParent> = ArrayList()
    private var childList:List<ModelChild> = ArrayList()
    var newList: ArrayList<Any> = ArrayList<Any>()
    var z = 0
    var g = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        initRecycler()

    }// end onCreate

    override fun onResume() {
        super.onResume()
        initDB()
    }

    private fun initDB() {
        parentList = db.queryDEPT()
        childList = db.queryITEM()
        if(parentList.isEmpty()){
            title = "No Records in DB"
        }else{
            title = "Parent List"
        }

        RecyclerAdapter1 = ViewAdapter(parents = parentList, context = applicationContext)
        (recyclerView as RecyclerView).adapter = RecyclerAdapter1

        //RecyclerAdapter2 = ViewChildAdapter(children = childList)
        //(recyclerView as RecyclerView).adapter = RecyclerAdapter2
    }

    private fun initRecycler() {
        val db = DBHelper(this)
        childList = db.queryITEM()
        parentList = db.queryDEPT()

        recyclerView = rv_parent

        var PL = parentList.size
        newList.clear()
        do {
            var DEPT: String = parentList[z].dept
            var ND:String = DEPT
            var PARENT_LIST_FK = parentList.get(z).fkD
            var PL_ST = ND+" "+PARENT_LIST_FK
            newList.add(PL_ST)

            println("========== Dept " + DEPT + " fkD " + PARENT_LIST_FK)
            val FK = PARENT_LIST_FK

            childList = db.queryALL(FK)
            var CL = childList.size

            for (a in 0..CL - 1) {
                var CHILD_ITEM = childList[a].item
                var NI:String = childList[a].item
                var CHILD_LIST_FK = childList[a].fkI
                var IL_ST = NI+" "+CHILD_LIST_FK
                newList.add(IL_ST)
                println("========== item " + CHILD_ITEM+" fkI "+CHILD_LIST_FK)
            }
            z++

            g++
        }
        while (z <= PL-1)


        var ui = newList.size
        g=0
        for(g in 0..ui-1){
            var N2 = newList[g]

            if(N2.toString().contains("1")){
                println("********************** We Found "+N2)

            }
            println("############### BOTH = "+N2)
        }

        recyclerView = this.findViewById(R.id.rv_parent)
        RecyclerAdapter1 = ViewAdapter(parents = parentList, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager!!

      /*recyclerView .apply {
            layoutManager = LinearLayoutManager(this@ViewActivity, LinearLayout.VERTICAL, false)
            adapter = ViewAdapter(parentList)
        }*/

        recyclerView.apply {
        layoutManager = LinearLayoutManager(this@ViewActivity, LinearLayout.VERTICAL, false)
        adapter = ViewChildAdapter(children = childList)

        }

        //recyclerView.apply{
            //layoutManager = LinearLayoutManager(this@ViewActivity, LinearLayout.VERTICAL, false)
            //adapter = ViewAdapter(parentList)
            //adapter = ViewChildAdapter(children = childList)
            //adapter = ParentAdapter(ParentDataFactory.getParents(7))// FROM NESTED RV
            //adapter = ViewAdapter(parentList).apply {ViewChildAdapter(children = childList)}
        //}
    }

}// end Class
