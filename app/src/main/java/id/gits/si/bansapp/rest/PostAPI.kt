package id.gits.si.bansapp.rest

import id.gits.si.bansapp.model.PostResponse
import retrofit2.Call
import retrofit2.http.*

interface PostAPI {
    @GET("?function=get_post")
    fun getPost(): Call<PostResponse>
    @FormUrlEncoded
    @POST("?function=insert_post")
    fun insertPost(
        @Field("post_title") post_title : String,
        @Field("post_body") post_body : String,
        @Field("post_image") post_image : String,
        @Field("post_time") post_time : String,
        @Field("post_credit") post_credit : String
    ): Call<PostResponse>
    @GET("?function=get_post_id")
    fun getPostID(
        @Field("post_id") post_id : Int
    ): Call<PostResponse>
    @FormUrlEncoded
    @POST("?function=update_post")
    fun updatePost(
        @Field("post_title") post_title : String,
        @Field("post_body") post_body : String,
        @Field("post_image") post_image : String,
        @Query("post_id") post_id : String
    ): Call<PostResponse>
    @DELETE("?function=delete_post")
    fun deletePost(
        @Query("post_id") post_id : String
    ): Call<PostResponse>


}