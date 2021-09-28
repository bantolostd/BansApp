package id.gits.si.bansapp.rest

import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.model.PostResponse
import retrofit2.Call
import retrofit2.http.*

interface PenggunaAPI {
    @GET("?function=get_pengguna")
    fun getPengguna(): Call<PenggunaResponse>
    @FormUrlEncoded
    @POST("?function=insert_pengguna")
    fun insertPengguna(
        @Field("pengguna_nama") pengguna_nama : String,
        @Field("pengguna_email") pengguna_email : String,
        @Field("pengguna_username") pengguna_username : String,
        @Field("pengguna_password") pengguna_password : String,
        @Field("pengguna_foto") pengguna_foto : String
    ): Call<PenggunaResponse>
    @GET("?function=get_pengguna_id")
    fun getPenggunaID(
        @Query("pengguna_id") pengguna_id : Int
    ): Call<PenggunaResponse>
    @FormUrlEncoded
    @POST("?function=update_pengguna")
    fun updatePengguna(
        @Field("pengguna_nama") pengguna_nama : String,
        @Field("pengguna_email") pengguna_email : String,
        @Field("pengguna_username") pengguna_username : String,
        @Field("pengguna_password") pengguna_password : String,
        @Field("pengguna_foto") pengguna_foto : String,
        @Query("pengguna_id") pengguna_id : String
    ): Call<PenggunaResponse>
    @DELETE("?function=delete_pengguna")
    fun deletePengguna(
        @Query("pengguna_id") pengguna_id : String
    ): Call<PenggunaResponse>


}