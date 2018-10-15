package com.androidstackoverflow.kotlintestview

class ModelParent() {

     var idD:Int = 0
     var dept:String = ""
     var fkD:Int = 0
     var children: List<ModelChild> = mutableListOf()
     //var children: List<ModelChild>  by Delegates.notNull()
     //constructor (children: List<ModelParent>) : this()

 }



