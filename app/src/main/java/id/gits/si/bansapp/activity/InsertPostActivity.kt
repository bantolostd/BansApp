package id.gits.si.bansapp.activity

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import id.gits.si.bansapp.rest.UploadImageNetworkConfig
import kotlinx.android.synthetic.main.activity_insert_post.*
import kotlinx.android.synthetic.main.activity_insert_post.btn_insert_image
import kotlinx.android.synthetic.main.activity_insert_post.et_post_body
import kotlinx.android.synthetic.main.activity_insert_post.et_post_title
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.action_bar
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import okhttp3.MultipartBody.Part.Companion.createFormData
import java.io.File
import id.gits.si.bansapp.model.UploadImageResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


class InsertPostActivity : AppCompatActivity() {
    private var selectedImage: Uri? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_insert_post)

        action_bar.setText("Tambah Post")
        btn_insert_image.setText("Tambah Post!")
        btn_insert.setText("Tambah Post!")

        btn_image_upload.setOnClickListener {
            pickImage()
            btn_insert.setVisibility(View.GONE)
            btn_insert_image.setVisibility(View.VISIBLE)
        }

        btn_insert.setOnClickListener {
            insertPost()
        }

        btnBack.setOnClickListener {
            goBackHome()
        }

        btn_insert_image.setVisibility(View.GONE)

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
                    btn_insert_image.setVisibility(View.VISIBLE)

                    btn_insert_image.setOnClickListener {
                        val toast = Toast.makeText(this@InsertPostActivity, selectedImage.toString(), Toast.LENGTH_LONG)
                        toast.show()
                        uploadImage(selectedImage!!)
                    }
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
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString())
            return ""
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    private fun uploadImage(contentURI: Uri) {
        val filePath = getRealPathFromURI(this, contentURI)
        val file = File(filePath)
        val mFile = RequestBody.create("multipart".toMediaTypeOrNull(), file) //membungkus file ke dalam request body
        val body: MultipartBody.Part = createFormData("file_gambar", file.getName(), mFile)

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
                        insertPost(file.getName())
                    }
                } else {
                    val toast = Toast.makeText(this@InsertPostActivity, "User gagal ditambahkan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                val toast = Toast.makeText(this@InsertPostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertPost(post_image : String = "default.jpg") {
        PostNetworkConfig().getService().insertPost(
            et_post_title.text.toString().trim(),
            et_post_body.text.toString().trim(),
            post_image,getWaktuSekarang().toString(),"1"
        ).enqueue(object: Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>?,
                response: Response<PostResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        val toast = Toast.makeText(this@InsertPostActivity, "Post berhasil ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    } else if(response.body()?.status == 2){
                        val toast = Toast.makeText(this@InsertPostActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@InsertPostActivity, "Post gagal ditambahkan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@InsertPostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWaktuSekarang(): LocalDateTime? {
        val sekarang = LocalDateTime.now()
        return sekarang
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}