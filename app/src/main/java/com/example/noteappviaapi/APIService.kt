package com.example.noteappviaapi

import com.example.noteappviaapi.Model.*
import retrofit2.Call
import retrofit2.http.*

interface APIService {

  @Headers("Content-Type: application/json")
    @POST("/auth/signup")
fun createUser(@Body model: userModel): Call<DefaultUserResponse>

  @POST("/auth/signin")
fun login(@Body model: userModel): Call<DefaultUserResponse>


@POST("/notes")
fun addNote(@Header("Authorization")token: String, @Body noteModel: noteModel): Call<addNoteResponseModel>

 @GET("/notes")
  fun ShowNote(@Header("Authorization")token: String): Call<List<addNoteResponseModel>>

  @PATCH("/notes/{id}")
  fun UpdateNote(@Header("Authorization")token: String,@Path(value="id") id: Int, @Body updateModel: updateModel): Call<updateModel>

  @DELETE("/notes/{id}")
  fun DeleteNote(@Header("Authorization")token: String,@Path(value="id") id: Int):Call<StatusModel>

}
