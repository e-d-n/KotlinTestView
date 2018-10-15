package com.androidstackoverflow.kotlintestview

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DBHelper(private val context: Context): SQLiteOpenHelper(context,DBHelper.DB_NAME,null,DBHelper.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_DEPT = "CREATE TABLE ${PARENT_TABLE} ($colidD INTEGER PRIMARY KEY,$colDept TEXT,$colPFK INTEGER);"
        val CREATE_TABLE_ITEM = "CREATE TABLE ${CHILD_TABLE} ($colidI INTEGER PRIMARY KEY,$colItem TEXT,$colCFK INTEGER);"
        db!!.execSQL(CREATE_TABLE_DEPT)
        db.execSQL(CREATE_TABLE_ITEM)

        Toast.makeText(context,"database was created",Toast.LENGTH_LONG).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_DEPT = "DROP TABLE IF EXISTS $PARENT_TABLE"
        val DROP_TABLE_ITEM = "DROP TABLE IF EXISTS $CHILD_TABLE"
        db!!.execSQL(DROP_TABLE_DEPT)
        db.execSQL(DROP_TABLE_ITEM)
        onCreate(db)
        Toast.makeText(context,"tables dropped new DB",Toast.LENGTH_LONG).show()
    }

    fun queryALL(fkI: Int): List<ModelChild> {
        val db = this.writableDatabase
        val childList = ArrayList<ModelChild>()
        val selectQuery = "SELECT  * FROM $CHILD_TABLE WHERE $colCFK = ?"
        //val selectQuery = "SELECT  * FROM ${CHILD_TABLE}"
        val cursor = db.rawQuery(selectQuery, arrayOf(fkI.toString()))
        //val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val contact = ModelChild()
                    contact.idI = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colidI)))
                    contact.item = cursor.getString(cursor.getColumnIndex(colItem))
                    contact.fkI = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colCFK)))
                    childList.add(contact)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return childList
    }

    fun getOneName(fkI: Int.Companion): ModelChild? {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $CHILD_TABLE WHERE $colCFK = ?"
        db.rawQuery(selectQuery, arrayOf(fkI.toString())).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = ModelChild()
                result.fkI = it.getInt(it.getColumnIndex(colCFK))
                result.item = it.getString(it.getColumnIndex(colItem))
                return result
            }
        }
        return null
    }

    fun querySPDept() :List<ModelParent> {
        val db = this.writableDatabase
        val contactList = mutableListOf<ModelParent>()
        //val contactList = java.util.ArrayList<ModelParent>()

        val selectQuery = "SELECT  * FROM ${PARENT_TABLE}"
        val cursor = db.rawQuery(selectQuery,null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val contact = ModelParent()
                    contact.idD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colidD)))
                    contact.dept = cursor.getString(cursor.getColumnIndex(colDept))
                    contact.fkD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colPFK)))
                    contactList.add(contact)


                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return contactList
    }

    fun queryDEPT(): List<ModelParent> {
        val db = this.writableDatabase
        val parentList = ArrayList<ModelParent>()
        val selectQuery = "SELECT  * FROM ${PARENT_TABLE}"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val contact = ModelParent()
                    contact.idD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colidD)))
                    contact.dept = cursor.getString(cursor.getColumnIndex(colDept))
                    contact.fkD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colPFK)))
                    parentList.add(contact)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return parentList
    }

    fun queryITEM(): List<ModelChild> {
        val db = this.writableDatabase
        val childList = ArrayList<ModelChild>()
        val selectQuery = "SELECT  * FROM ${CHILD_TABLE}"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val contact = ModelChild()
                    contact.idI = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colidI)))
                    contact.item = cursor.getString(cursor.getColumnIndex(colItem))
                    contact.fkI = Integer.parseInt(cursor.getString(cursor.getColumnIndex(colCFK)))
                    childList.add(contact)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return childList
    }

    fun insertDEPT(values: ContentValues): Long {
        val db = this.writableDatabase
        val idD = db.insert(PARENT_TABLE, null, values)
        return idD
    }

    fun updateDEPT(values: ContentValues, selection: String, selectionargs: Array<String>):Int{
        val db = this.writableDatabase
        val dept = db.update(PARENT_TABLE,values,selection,selectionargs)
        return dept
    }

    fun insertITEM(values: ContentValues): Long {
        val db = this.writableDatabase
        val idI = db.insert(CHILD_TABLE, null, values)
        return idI
    }

    fun updateITEM(values: ContentValues, selection: String, selectionargs: Array<String>): Int {
        val db = this.writableDatabase
        val count = db.update(CHILD_TABLE, values, selection, selectionargs)
        return count
    }

    fun deleteDEPT(productname: String): Boolean {
        var result = false
        val query = "SELECT * FROM $PARENT_TABLE WHERE $colDept= \"$productname\""
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = Integer.parseInt(cursor.getString(0))
            db.delete(PARENT_TABLE, "$colidD = ?", arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    fun deleteITEM(productname: String): Boolean {
        var result = false
        val query = "SELECT * FROM $CHILD_TABLE WHERE $colItem= \"$productname\""
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = Integer.parseInt(cursor.getString(0))
            db.delete(CHILD_TABLE, "$colidI = ?", arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "GroceryList.db"
        private val PARENT_TABLE = "Parent"
        private val colidD = "idD"
        private val colDept = "Dept"
        private val colPFK = "fkD"
        private val CHILD_TABLE = "Child"
        private val colidI = "idI"
        private val colItem = "Item"
        private val colCFK = "fkI"
    }
}