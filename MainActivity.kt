package com.androidstackoverflow.kotlintestview

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }// end onCreate

    fun onEnterParentData(view: View){
        val intent = Intent(this,EnterParentActivity::class.java)
        intent.putExtra("FROM", "N")
        startActivity(intent)
    }

    fun onViewParentData(view: View){
        val intent = Intent(this,ViewParentActivity::class.java)
        intent.putExtra("FROM", "N")
        startActivity(intent)
    }

    fun onEnterChildData(view: View){
        val intent = Intent(this,EnterChildActivity::class.java)
        startActivity(intent)
    }

    fun onViewChildData(view: View){
        val intent = Intent(this,ViewChildActivity::class.java)
        startActivity(intent)
    }

    fun onViewAll(view: View){
        val intent = Intent(this,ViewActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed(){
        theTOAST()
    }

    fun theTOAST(){

        val toast = Toast.makeText(this, "No Going Back", Toast.LENGTH_LONG)
        val view = toast.view

        view.setBackgroundColor(Color.TRANSPARENT)
        val text = view.findViewById(android.R.id.message) as TextView

        text.setTextColor(Color.BLUE)
        text.textSize = (20F)
        toast.show()
    }

}//end Class
