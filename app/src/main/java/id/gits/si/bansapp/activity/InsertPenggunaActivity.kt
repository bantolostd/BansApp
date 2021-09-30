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
import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.rest.UploadImageNetworkConfig
import kotlinx.android.synthetic.main.activity_insert_post.*
import kotlinx.android.synthetic.main.activity_insert_post.btn_insert_image
import kotlinx.android.synthetic.main.activity_insert_post.et_pengguna_email
import kotlinx.android.synthetic.main.activity_insert_post.et_pengguna_nama
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
import id.gits.si.bansapp.rest.PenggunaNetworkConfig
import kotlinx.android.synthetic.main.activity_insert_pengguna.*
import kotlinx.android.synthetic.main.activity_insert_post.btn_image_upload
import kotlinx.android.synthetic.main.activity_insert_post.btn_insert
import kotlinx.android.synthetic.main.activity_insert_post.iv_post_image_preview
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody


class InsertPenggunaActivity : AppCompatActivity() {
    private var selectedImage: Uri? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_insert_pengguna)

        action_bar.setText("Tambah Pengguna")
        btn_insert_image.setText("Tambah Pengguna!")
        btn_insert.setText("Tambah Pengguna!")

        btn_image_upload.setOnClickListener {
            pickImage()
            btn_insert.setVisibility(View.GONE)
            btn_insert_image.setVisibility(View.VISIBLE)
        }

        btn_insert.setOnClickListener {
            insertPengguna()
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
                        insertPengguna(file.getName())
                        val toast = Toast.makeText(this@InsertPenggunaActivity, "Pengguna berhasil ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    }
                } else {
                    val toast = Toast.makeText(this@InsertPenggunaActivity, "Pengguna gagal ditambahkan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
                val toast = Toast.makeText(this@InsertPenggunaActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertPengguna(post_image : String = "user_default.jpg") {
        PenggunaNetworkConfig().getService().insertPengguna(
            et_pengguna_nama.text.toString().trim(),
            et_pengguna_email.text.toString().trim(),
            et_pengguna_username.text.toString().trim(),
            et_pengguna_password.text.toString().trim(),
            post_image
        ).enqueue(object: Callback<PenggunaResponse> {
            override fun onResponse(
                call: Call<PenggunaResponse>?,
                response: Response<PenggunaResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        val toast = Toast.makeText(this@InsertPenggunaActivity, "Pengguna berhasil ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome()
                    } else if(response.body()?.status == 2){
                        val toast = Toast.makeText(this@InsertPenggunaActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@InsertPenggunaActivity, "Pengguna gagal ditambahkan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@InsertPenggunaActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
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
        val intent = Intent(this, PenggunaMainActivity::class.java)
        startActivity(intent)
    }
}