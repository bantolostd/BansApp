package id.gits.si.bansapp.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.DetailPostResponse
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.model.UploadImageResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import id.gits.si.bansapp.rest.UploadImageNetworkConfig
import id.gits.si.bansapp.support.cekLogin
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_post.*
import kotlinx.android.synthetic.main.activity_update_post.btn_image_upload
import kotlinx.android.synthetic.main.activity_update_post.btn_insert_image
import kotlinx.android.synthetic.main.activity_update_post.et_post_body
import kotlinx.android.synthetic.main.activity_update_post.et_pengguna_nama
import kotlinx.android.synthetic.main.activity_update_post.iv_post_image_preview
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.action_bar
import kotlinx.android.synthetic.main.toolbar_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.btnBack
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdatePostActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private var selectedImage: Uri? = null
    private var temp_post_image = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLogin(pengguna_id, this)

        setContentView(R.layout.activity_update_post)

        action_bar.setText("Update Post")
        btn_insert_image.setText("Update Post!")
        btn_insert.setText("Update Post!")
        btn_delete.setText("Hapus Post!")

        val post_id = intent.getStringExtra("post_id").toString()

        getDetailPost(post_id)

        btnBack.setOnClickListener {
            goBackHome()
        }

        btn_insert.setOnClickListener {
            updatePost(post_id, temp_post_image)
        }

        btn_image_upload.setOnClickListener{
            pickImage()
        }

        btn_delete.setOnClickListener {
            deletePost(post_id)
        }

        btn_insert_image.setVisibility(View.GONE)

    }

    fun getDetailPost(post_id : String) {
        PostNetworkConfig().getService().getPostID(
            post_id.toInt()
        ).enqueue(object: Callback<DetailPostResponse> {
            override fun onResponse(
                call: Call<DetailPostResponse>?,
                response: Response<DetailPostResponse>?
            ) {
                if (response!!.isSuccessful){
                    val data = response.body()!!
                    et_pengguna_nama.setText(data.postTitle)
                    et_post_body.setText(data.postBody)
                    temp_post_image = data.postImage.toString()
                    val URL_FOTO = "http://192.168.100.125/gits_api/images/"
                    Glide.with(this@UpdatePostActivity).load(URL_FOTO+data.postImage).centerCrop().into(iv_post_image_preview)
                } else {
                    val toast = Toast.makeText(this@UpdatePostActivity, "Post tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<DetailPostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@UpdatePostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    private fun pickImage(){
        Intent(Intent.ACTION_PICK).also{
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICKER)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_IMAGE_PICKER ->{
                    selectedImage = data?.data
                    iv_post_image_preview.setImageURI(selectedImage)
                    btn_insert.setVisibility(View.GONE)
                    btn_insert_image.setVisibility(View.VISIBLE)
                    et_post_image.setText("update")

                    btn_insert_image.setOnClickListener {
                        uploadImage(selectedImage!!, intent.getStringExtra("post_id").toString())
                    }
/*
                    btn_insert_image.setOnClickListener {
                        if(post_image != et_post_image.text.toString()) {
                            updatePost(post_id, et_post_image.text.toString())
                        } else {
                            updatePost(post_id, post_image)
                        }
                    }*/
                }
            }
        }
    }
    companion object{
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "getRealPathFromURI Exception : " + e.toString())
            return ""
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    private fun uploadImage(contentURI: Uri, post_id: String) {
        val filePath = getRealPathFromURI(this, contentURI)
        val file = File(filePath)
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file) //membungkus file ke dalam request body
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("file_gambar", file.getName(), mFile)

        UploadImageNetworkConfig().getService().uploadImage(
            body
        ).enqueue(object: Callback<UploadImageResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<UploadImageResponse>?,
                response: Response<UploadImageResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        updatePost(post_id, file.getName())
                        val toast = Toast.makeText(this@UpdatePostActivity, "Pengguna berhasil ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    }
                } else {
                    val toast = Toast.makeText(this@UpdatePostActivity, "Pengguna gagal ditambahkan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                val toast = Toast.makeText(this@UpdatePostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    fun updatePost(post_id : String, post_image : String) {
        PostNetworkConfig().getService().updatePost(
            et_pengguna_nama.text.toString().trim(),
            et_post_body.text.toString().trim(),
            post_image,
            post_id
        ).enqueue(object: Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>?,
                response: Response<PostResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        val toast = Toast.makeText(this@UpdatePostActivity, "Post berhasil diperbaharui!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    }
                } else {
                    val toast = Toast.makeText(this@UpdatePostActivity, "Post gagal diperbaharui!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@UpdatePostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    fun deletePost(post_id: String) {
        PostNetworkConfig().getService().deletePost(
            post_id).enqueue(object: Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>?,
                response: Response<PostResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        val toast = Toast.makeText(this@UpdatePostActivity, "Post berhasil dihapus!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    }
                } else {
                    val toast = Toast.makeText(this@UpdatePostActivity, "Post gagal dihapus!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@UpdatePostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}