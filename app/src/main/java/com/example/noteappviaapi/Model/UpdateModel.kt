package com.example.noteappviaapi.Model

data class updateModel(val id:Int, val title:String, val body:String, val status:String="Active", val userid: Int= 18118,
                       val created:String,
                       val updated :String
                       )
