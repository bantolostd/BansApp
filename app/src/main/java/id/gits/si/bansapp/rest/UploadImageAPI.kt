package id.gits.si.bansapp.rest

import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.model.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface UploadImageAPI {
    @Multipart
    @POST("?function=upload_image")
    fun uploadImage(
        @Part file_gambar: MultipartBody.Part
    ): Call<UploadImageResponse>



}