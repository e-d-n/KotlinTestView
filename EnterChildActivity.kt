package com.androidstackoverflow.kotlintestview

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_enter_child.*
import java.util.*


class EnterChildActivity : AppCompatActivity() {

    var idI = 0
    var fkI = 0
    var from = ""
    private var ET = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_child)

        try {
            val bundle: Bundle = intent.extras
            idI = bundle.getInt("MainActId", 0)
            fkI = bundle.getInt("CFK",0)
            from = bundle.getString("FROM","")
            ET = bundle.getString("ET","")
            if(from == "U"){
                //btnSaveDeptData.text = "Update Person"
                btnSaveItemData.visibility = View.INVISIBLE
                btnViewItemList.visibility = View.INVISIBLE
                btnDeleteItem.visibility = View.VISIBLE
                btnEditItemData.visibility = View.VISIBLE
                etItemData.setText(ET)
                if(fkI != 0){
                    etItemFK.setText(fkI.toString())
                }
            }else{
                //btnSaveDeptData.text = "Add New Person"
                btnSaveItemData.visibility = View.VISIBLE
                btnViewItemList.visibility = View.VISIBLE
                btnDeleteItem.visibility = View.INVISIBLE
                btnEditItemData.visibility = View.INVISIBLE
                etItemData.setText(ET)
                if(fkI != 0){
                    etItemFK.setText(fkI.toString())
                }
            }
            if (idI != 0) {

                //etPerson.setText(bundle.getString("ET"))
                //etPerson.setText("We Have no Records")
            }
        } catch (ex: Exception) {
        }

        btnEditItemData.setOnClickListener {
            if (etItemData.text.toString().equals("")) {
                message("ENTER Dept")
                etItemData.requestFocus()
                return@setOnClickListener
            }

            if (etItemFK.text.toString().equals("")|| etItemFK.text.toString().toInt() == 0) {
                message("ENTER foreign key")
                etItemFK.requestFocus()
                return@setOnClickListener
            }

            val dbManager = DBHelper(this)
            val values = ContentValues()
            values.put("item", etItemData.text.toString())
            values.put("fkI",Integer.parseInt(etItemFK.text.toString()))

            if (idI > 0) {
                val selectionArs = arrayOf(idI.toString())
                val mID = dbManager.updateITEM(values, "idI=?", selectionArs)

                if (mID > 0) {
                    tvMsg.setTextColor(Color.RED)
                    message("Updated Dept successfully")
                    //Timer().schedule(800){
                        nextACTIVITY()
                    //}
                } else {
                    message("Failed to Update Dept")
                }
            }
        }

        btnSaveItemData.setOnClickListener {
            if (etItemData.text.toString().equals("")) {
                message("ENTER Item")
                etItemData.requestFocus()
                return@setOnClickListener
            }

            if (etItemFK.text.toString().equals("") || etItemFK.text.toString().toInt() == 0) {
                message("ENTER foreign key")
                etItemFK.requestFocus()
                return@setOnClickListener
            }

            val dbManager = DBHelper(this)
            val values = ContentValues()
            values.put("item", etItemData.text.toString())
            values.put("fkI",Integer.parseInt(etItemFK.text.toString()))
            if (idI == 0) {
                val mID = dbManager.insertITEM(values)

                if (mID > 0) {
                    tvMsg.setTextColor(Color.RED)
                    message("ADDED Item successfully")
                    //Timer().schedule(800){
                        nextACTIVITY()
                    //}
                } else {
                    message("Failed to add Item")
                }
            }
        }

        btnDeleteItem.setOnClickListener {
            if (etItemData.text.toString().equals("")) {
                message("No Match Found")
                return@setOnClickListener
            }
            doCustom()
        }

    }// end onCreate

    fun doCustom() {
        /* This method uses the custom_dialog.xml file created for greater control over
       the styling of the Custom Alert Dialog for various screen sizes and to be
       able to set the text size of the dialog message text
       */
        val makeDialog = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this).setView(makeDialog)
        val mAlertDialog = mBuilder.show()

        val btnYES = makeDialog.findViewById<Button>(R.id.btnYES)
        val btnNO = makeDialog.findViewById<Button>(R.id.btnNO)
        mAlertDialog.setCancelable(false)

        btnYES.setOnClickListener {
            removeITEM()
            mAlertDialog.dismiss()
        }

        btnNO.setOnClickListener {
            message("Record NOT Deleted")
            etItemData.setText("")
            //Timer().schedule(800){
                thisACTIVITY()
            //}
            mAlertDialog.dismiss()
        }
        mAlertDialog.show()
    }

    private fun removeITEM() {

        val dbHandler = DBHelper(this)

        val result = dbHandler.deleteITEM(etItemData.text.toString())

        if (result) {
            etItemData.setText("")
            message("Record Removed")
            //Timer().schedule(1000){
                thisACTIVITY()
            //}
        }else{
            etItemData.setText("NO MATCH -> click View Item List")
            btnViewItemList.visibility = View.VISIBLE
            btnEditItemData.visibility = View.INVISIBLE
            btnDeleteItem.visibility = View.INVISIBLE
            btnSaveItemData.visibility = View.INVISIBLE
            message("NO Match Found")
        }
    }

    fun nextACTIVITY(){
        val intent = Intent(this, ViewChildActivity::class.java)
        startActivity(intent)
    }

    fun thisACTIVITY(){
        val intent = Intent(this, EnterChildActivity::class.java)
        intent.putExtra("FROM", "N")
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    fun onViewItemList(view: View){
        nextACTIVITY()
    }

    fun message(msg:String){
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvMsg.visibility = View.VISIBLE
                tvMsg.text = msg
            }
            override fun onFinish() {
                tvMsg.visibility = View.INVISIBLE
                tvMsg.text = ""
            }
        }.start()
    }

    fun onBACK(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}// end Class
