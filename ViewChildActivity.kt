package com.androidstackoverflow.kotlintestview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

class ViewChildActivity : AppCompatActivity() {

    private var RecyclerAdapter: ChildAdapter? = null
    private var recyclerView: RecyclerView? = null
    private val db = DBHelper(this)
    private var childList:List<ModelChild> = ArrayList()
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_child)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }// end onCreate

    override fun onResume() {
        super.onResume()
        initDB()
    }

    // This is ONLY called when Activity is in onResume state
    private fun initDB() {
        childList = db.queryITEM()
        if(childList.isEmpty()){
            title = "No Records in DB"
        }else{
            title = "Child List"
        }

        RecyclerAdapter = ChildAdapter(childList = childList, context = applicationContext)
        (recyclerView as RecyclerView).adapter = RecyclerAdapter
    }

    private fun initViews() {

        recyclerView = this.findViewById(R.id.rvChildView)
        RecyclerAdapter = ChildAdapter(childList = childList, context = applicationContext)
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
                    val intent = Intent(this, EnterChildActivity::class.java)
                    startActivity(intent)
                }
            }
            // CODE below manages HOME Button
            val id = item.itemId
            if (id == android.R.id.home) {
                val intent = Intent(this, EnterChildActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(){
        val intent = Intent(this,EnterChildActivity::class.java)
        startActivity(intent)
    }
}// end Class
