package com.androidstackoverflow.kotlintestview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_view_all.*
import java.util.*
import kotlin.collections.ArrayList

class ViewAllActivity : AppCompatActivity() {

    var X = 0
    var Y = 0
    var c = 1
    var t = 0
    var z = 0
    var g = 0
    //private var RecyclerAdapter: PersonRecyclerAdapter? = null
    //private var recyclerView: RecyclerView? = null
    private val db = DBHelper(this)
    private var parentList:List<ModelParent> = ArrayList()
    private var childList:List<ModelChild> = ArrayList()
    //val newList = ArrayList<Any>()
    //private var newList:List<Any> = ArrayList()
    var newList: ArrayList<Any> = ArrayList<Any>()
    //private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        btnShowLoop.setOnClickListener {
            //showLOOP()
            //doLOOP()
            //theGET()
            mergTWO()
        }



        btnGetFKI.setOnClickListener {
            val db = DBHelper(this)
            //var FK = etFKI.text.toString()
            var FK = 1
            //childList = db.queryALL()
            childList = db.queryALL(FK.toInt())
            var XX = childList.size
            if(XX == 3){
                var H = childList[0].item
                var I = childList[1].item
                var K = childList[2].item
                etFKI.setText("item $H"+"\n,"+"item $I item $K"+"\n,"+" SIZE  $XX ")

            }else if(XX == 1){
                var H = childList[0].item
                etFKI.setText("item $H  SIZE  $XX ")
            }



        }
    }// end onCreate

    fun mergTWO(){
        val db = DBHelper(this)
        childList = db.queryITEM()
        parentList = db.queryDEPT()

        var PL = parentList.size
        newList.clear()
        do {
            var DEPT: String = parentList[z].dept
            var ND:String = DEPT
            //newList.add(ND)
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
                //newList.add(NI)
                var CHILD_LIST_FK = childList[a].fkI
                var IL_ST = NI+" "+CHILD_LIST_FK
                newList.add(IL_ST)
                println("========== item " + CHILD_ITEM+" fkI "+CHILD_LIST_FK)
            }
            z++
            //var ND = parentList[g].dept
            //newList.add(ND)
            //var NI = childList[g].item
            //newList.add(NI)
            g++
        }
        while (z <= PL-1)

        var ui = newList.size
        g=0
        //var N1 = newList[0]
        //println("############### ND = "+N1)
        //var N1 = newList[g]
        //println("############### ND = "+N1)
        for(g in 0..ui-1){
            var N2 = newList[g]

            if(N2.toString().contains("1")){
                println("********************** We Found "+N2)
            }
            println("############### BOTH = "+N2)
        }
    }

    fun theGET(){

        val db = DBHelper(this)
        childList = db.queryITEM()
        parentList = db.queryDEPT()
        var PL = parentList.size

        do {
            var DEPT: String = parentList[z].dept
            var PARENT_LIST_FK = parentList.get(z).fkD
            println("========== Dept " + DEPT + " fkD " + PARENT_LIST_FK)
            val FK = PARENT_LIST_FK
            childList = db.queryALL(FK)
            var CL = childList.size
            for (a in 0..CL - 1) {
                var CHILD_ITEM = childList[a].item
                var CHILD_LIST_FK = childList[a].fkI
                println("========== item " + CHILD_ITEM+" fkI "+CHILD_LIST_FK)
            }
            z++

        }
        while (z <= PL-1)

    }




    //var P: Int= 0
    //var C1: Int =  0

    /*fun doLOOP(){
        val db = DBHelper(this)
        childList = db.queryITEM()
        parentList = db.queryDEPT()
        var PL = parentList.size
        var CL = childList.size
        var x = 0
        //loop@
        do {
            //var I: Int = parentList.get(x).idD
            P = parentList.get(x).fkD
            //var C1: Int = childList.get(x).fkI
            var D: String = parentList[x].dept
            //var C:String = childList[x].item
            //println("========== idD " + I)
            println("========== fkD " + P +" Dept "+D)
            //println("========== dkI " + C1)
            println("****************************** do LOOP "+x+" SIZE PL "+PL )
            x++
            //break@loop
        } while(x <= PL -1)// x <= PL -1


        var y = 0
        var z = 0
        do{
            if(P!=C1) {
                //var P: Int = parentList.get(x).fkD
                C1 = childList.get(y).fkI
                var C: String = childList[z].item
                println("========== dkI " + C1 + " Item " + C)
                println("****************************** do LOOP " + y + " SIZE CL " + CL)
                z++
                y++
            }
        }while (P != C1)

    }*/

    fun showLOOP() {
        val db = DBHelper(this)
        var FK = etFKI.text.toString()

        childList = db.queryITEM()
        parentList = db.queryDEPT()

        var PL = parentList.size
        var CL = childList.size

        loop@ for (a in 1..PL - 1) {
            var N: String = parentList[t].dept
            // NOTE two syntax here [t] and get(t)
            var I: Int = parentList.get(t).idD
            var P: Int = parentList.get(t).fkD
            var C1: Int = childList.get(t).fkI

            println("################### id " + I.toString() + " for " + N)

            println("value a:$a")

            //for (b in 1..CL-1){
            //if (C1 != P){
            // break@loop
            //}
            while (C1 != P)

                break@loop
            var H = childList[0].item
            var J = childList[1].item
            var K = childList[3].item
            println("HHHHHHHHHH item " + H)
            println("IIIIIIIIII item " + J)
            println("KKKKKKKKKK item " + K)

            while(a == 2)

                H = ""
                J = ""
                K = ""
                var L = childList[2].item
                println("HHHHHHHHHH item " + L)


            //etFKI.setText("item $H"+"\n,"+"item $I item $K"+"\n,"+" SIZE  $CL ")
            //println(" b:$b")

            //}
        }

        println("Loop done")
    }



    fun onDoIt(view: View){

        // Elvis Operator
        //val nullableName: String? = null
        //val name1 = nullableName ?: "Guest"
        //val name2 = if(nullableName != null) nullableName else "A Rotten Guest"
        // println("################################## I am your "+name1+"  "+name2)


        initDB()


        /*for (t in 0..X-1) {
            var N:String = parentList[t].dept
            // NOTE two syntax here [t] and get(t)
            var I:Int = parentList.get(t).idD
            var P:Int = parentList.get(t).fkD
            var C1:Int = childList.get(I).fkI

            println("################### id "+I.toString()+" for "+N)
            while(c <= P){

                var C2:String = childList[0].item

                //var B:String = parentList[0].idD.toString()
                println("$$$$$$$$$$$$$$$$$$$$$ childList ITEM "+C2+" fkD "+P)

                c++


            }

        }*/

        while (t <= X-1) {
            var N:String = parentList[t].dept
            // NOTE two syntax here [t] and get(t)
            var I:Int = parentList.get(t).idD
            var P:Int = parentList.get(t).fkD
            var C1:Int = childList.get(t).fkI
            //println("################### id "+I.toString()+" for "+N)
            //for(c in 0..Y-1)

            //while(c == P){

            if(C1 == P) {
                println("################### id "+I.toString()+" for "+N)
                var C2: String = childList[0].item
                //C1++
                P++
                //var B:String = parentList[0].idD.toString()
                println("$$$$$$$$$$$$$$$$$$$$$ childList ITEM " + C2 + " fkD " + P + " C = " + C1)
                c++
            }
            t++
            //println("################### id "+I.toString()+" for "+N)
        }

    }

    private fun initDB() {
        parentList = db.querySPDept()
        childList = db.queryITEM()
        if (parentList.isEmpty()) {
            title = "No Records in DB"
        } else {
            X = parentList.size
            Y = childList.size
            println("**************************************** parentList SIZE " + X+" childList SIZE "+Y)
            title = "SP View Activity"
        }
    }

    fun onBACK(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}// end Class



