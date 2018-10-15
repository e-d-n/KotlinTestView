package com.androidstackoverflow.kotlintestview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

class ViewParentActivity : AppCompatActivity() {

    private var RecyclerAdapter: ParentAdapter? = null
    private var recyclerView: RecyclerView? = null
    private val db = DBHelper(this)
    private var parentList:List<ModelParent> = ArrayList()
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()

    }// end onCreate


    override fun onResume() {
        super.onResume()
        initDB()
    }

    // This is ONLY called when Activity is in onResume state
    private fun initDB() {
        parentList = db.queryDEPT()
        if(parentList.isEmpty()){
            title = "No Records in DB"
        }else{
            title = "Parent List"
        }

        RecyclerAdapter = ParentAdapter(parentList = parentList, context = applicationContext)
        (recyclerView as RecyclerView).adapter = RecyclerAdapter
    }

    private fun initViews() {

        recyclerView = this.findViewById(R.id.rvParentView)
        RecyclerAdapter = ParentAdapter(parentList = parentList, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.addNote -> {
                    val intent = Intent(this, EnterParentActivity::class.java)
                    intent.putExtra("FROM","N")// ADD NEW NOTE
                    startActivity(intent)
                }
            }
            // CODE below manages HOME Button
            val id = item.itemId
            if (id == android.R.id.home) {
                val intent = Intent(this, EnterParentActivity::class.java)
                intent.putExtra("FROM","N")// ADD NEW NOTE
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(){
        val intent = Intent(this,EnterParentActivity::class.java)
        startActivity(intent)
    }

}// end Class
