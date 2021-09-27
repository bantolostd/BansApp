package id.gits.si.bansapp.rest

import retrofit2.Call
import id.gits.si.bansapp.model.UserResponse
import retrofit2.http.*

interface Api {
    @GET("api.php?function=get_user")
    fun getUser(): Call<UserResponse>
    @FormUrlEncoded
    @POST("?function=insert_user")
    fun insertUser(
        @Field("nama") nama : String,
        @Field("email") email : String,
        @Field("no_hp") no_hp : String,
        @Field("alamat") alamat : String,
        @Field("instagram") instagram : String
    ): Call<UserResponse>
    @GET("function=get_user_id")
    fun getUserID(
        @Field("id") id : Int
    ): Call<UserResponse>
    @FormUrlEncoded
    @POST("?function=update_user")
    fun updateUser(
        @Field("nama") nama : String,
        @Field("email") email : String,
        @Field("no_hp") no_hp : String,
        @Field("alamat") alamat : String,
        @Field("instagram") instagram : String,
        @Query("id") id : String
    ): Call<UserResponse>
    @DELETE("?function=delete_user")
    fun deleteUser(
        @Query("id") id: String
    ): Call<UserResponse>


}