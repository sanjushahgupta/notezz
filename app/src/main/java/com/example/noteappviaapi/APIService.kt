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
  fun DeleteNote(@Header("Authorization")token: String,@Path(value="id") id: Int):Call<Void>

 @POST("/auth/update")
 fun UpdateUserAccount(@Header("Authorization")token: String, @Body updateAccountRequestModel: UpdateAccountRequestModel ): Call<Void>

    @POST("/auth/update")
    fun UpdateUserAccountusernamenadpassword(@Header("Authorization")token: String, @Body updateEmail: UpdateEmail ): Call<Void>


  @POST("/auth/send-login-link")
    fun Onetimeloginlink(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): Call<Void>

    @DELETE("/auth/delete")
    fun DeleteAccount(@Header("Authorization")token: String) : Call<Void>

}
