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
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_enter_parent.*



class EnterParentActivity : AppCompatActivity() {

    var idD = 0
    var fkD = 0
    var from = ""
    private var ET = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_parent)

        try {
            val bundle: Bundle = intent.extras
            idD = bundle.getInt("MainActId", 0)
            fkD = bundle.getInt("PFK",0)
            from = bundle.getString("FROM","")
            ET = bundle.getString("ET","")
            if(from == "U"){
                //btnSaveDeptData.text = "Update Person"
                btnSaveDeptData.visibility = View.INVISIBLE
                btnViewDeptList.visibility = View.INVISIBLE
                btnDeleteDept.visibility = View.VISIBLE
                btnEditDept.visibility = View.VISIBLE
                etDeptData.setText(ET)
                if(fkD != 0){
                    etDeptFK.setText(fkD.toString())
                }

            }else{
                //btnSaveDeptData.text = "Add New Person"
                btnSaveDeptData.visibility = View.VISIBLE
                btnViewDeptList.visibility = View.VISIBLE
                btnDeleteDept.visibility = View.INVISIBLE
                btnEditDept.visibility = View.INVISIBLE
                etDeptData.setText(ET)
                if(fkD != 0){
                    etDeptFK.setText(fkD.toString())
                }
            }
            if (idD != 0) {

                //etPerson.setText(bundle.getString("ET"))
                //etPerson.setText("We Have no Records")
            }
        } catch (ex: Exception) {
        }


        btnEditDept.setOnClickListener {

            if (etDeptData.text.toString().equals("")) {
                message("ENTER Dept")
                etDeptData.requestFocus()
                return@setOnClickListener
            }

            if (etDeptFK.text.toString().equals("") || etDeptFK.text.toString().toInt() == 0) {
                message("ENTER foreign key")
                etDeptFK.requestFocus()
                return@setOnClickListener
            }

            val dbManager = DBHelper(this)
            val values = ContentValues()
            values.put("dept", etDeptData.text.toString())
            values.put("fkD",Integer.parseInt(etDeptFK.text.toString()))

            if (idD > 0) {
                // IN UPDATE idD needs to be idD > 0
                val selectionArs = arrayOf(idD.toString())
                val mID = dbManager.updateDEPT(values, "idD=?", selectionArs)

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

        btnSaveDeptData.setOnClickListener{
            if (etDeptData.text.toString().equals("")) {
                message("ENTER Dept")
                etDeptData.requestFocus()
                return@setOnClickListener
            }

            if (etDeptFK.text.toString().equals("") || etDeptFK.text.toString().toInt() == 0) {
                message("ENTER foreign key")
                etDeptFK.requestFocus()
                return@setOnClickListener
            }

            val dbManager = DBHelper(this)
            val values = ContentValues()
            values.put("dept", etDeptData.text.toString())
            values.put("fkD",Integer.parseInt(etDeptFK.text.toString()))
            //values.put("fkD",etDeptFK.text.toString().toInt())
            // the put MUST MATCH the Model <--- READ
            if (idD == 0) {
                // In INSERT or SAVE idD = 0 see updateDeptData
                val mID = dbManager.insertDEPT(values)

                if (mID > 0) {
                    tvMsg.setTextColor(Color.RED)
                    message("ADDED Dept successfully")
                    //Timer().schedule(800){
                        nextACTIVITY()
                    //}
                } else {
                    message("Failed to Add Dept")
                }
            }
        }

        btnDeleteDept.setOnClickListener {
            if (etDeptData.text.toString().equals("")) {
                message("No Match Found")
                return@setOnClickListener
            }
            doCustom()
        }

    }// end onCreate

    fun nextACTIVITY(){
        val intent = Intent(this, ViewParentActivity::class.java)
        startActivity(intent)
    }

    fun thisACTIVITY(){
        val intent = Intent(this, EnterParentActivity::class.java)
        intent.putExtra("FROM", "N")
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    fun onViewDeptList(view: View){
        nextACTIVITY()
    }

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
            removeDEPT()
            mAlertDialog.dismiss()
        }

        btnNO.setOnClickListener {
            message("Record NOT Deleted")
            etDeptData.setText("")
            //Timer().schedule(800){
                thisACTIVITY()
            //}
            mAlertDialog.dismiss()
        }
        mAlertDialog.show()
    }

    private fun removeDEPT() {

        val dbHandler = DBHelper(this)

        val result = dbHandler.deleteDEPT(etDeptData.text.toString())

        if (result) {
            etDeptData.setText("")
            message("Record Removed")
            //Timer().schedule(1000){
                thisACTIVITY()
            //}
        }else{
            etDeptData.setText("NO MATCH -> click View Dept List")
            btnViewDeptList.visibility = View.VISIBLE
            btnEditDept.visibility = View.INVISIBLE
            btnDeleteDept.visibility =View.INVISIBLE
            btnSaveDeptData.visibility = View.INVISIBLE
            message("NO Match Found")
        }
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
