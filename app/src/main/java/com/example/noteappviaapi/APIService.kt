package com.example.noteappviaapi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {

  @Headers("Content-Type: application/json")
    @POST("/auth/signup")
fun createUser(@Body model: userModel): Call<DefaultUserResponse>

  @POST("/auth/signin")
fun login(@Body model: userModel): Call<DefaultUserResponse>

@POST("/notes")
fun addNote(@Body noteModel: noteModel): Call<addNoteResponseModel>
}
