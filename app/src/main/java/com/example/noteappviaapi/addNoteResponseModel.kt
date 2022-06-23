package com.example.noteappviaapi

data class addNoteResponseModel(val body: String, val title : String,
                                val status:String, val userId: Int,
                                val id:Int, val created:String,
                                val updated :String
                              )
