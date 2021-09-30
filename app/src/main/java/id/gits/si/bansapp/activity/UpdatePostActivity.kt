package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_post.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.action_bar
import kotlinx.android.synthetic.main.toolbar_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_post)

        action_bar.setText("Update Post")
        btn_insert_image.setText("Update Post!")
        btn_delete.setText("Hapus Post!")

        val post_id = intent.getStringExtra("post_id").toString()

//        getPostID(post_id)
        val post_title = intent.getStringExtra("post_title")
        val post_body = intent.getStringExtra("post_body")
        val post_image = intent.getStringExtra("post_image")
//        val post_time = intent.getStringExtra("post_time")
//        val post_credit = intent.getStringExtra("post_credit")

        et_pengguna_nama.setText(post_title)
        et_pengguna_email.setText(post_body)
        et_post_image.setText(post_image)

        btnBack.setOnClickListener {
            goBackHome()
        }

        btn_image_upload.setOnClickListener{

        }

        btn_delete.setOnClickListener {
            deletePost(post_id)
        }

        btn_insert_image.setOnClickListener {
            if(post_image !== et_post_image.text.toString()) {
                insertPost(post_id, et_post_image.text.toString())
            } else {
                insertPost(post_id, post_image)
            }
        }

    }
/*
    fun getPostID(post_id: String) {
        PostNetworkConfig().getService().getPostID(
            post_id.toInt()
        ).enqueue(object: Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>?,
                response: Response<PostResponse>?
            ) {
                if (response!!.isSuccessful){
                    val result = response.body()!!
                    if (response.body()?.status == 1){
                        setViewPost(result)
                    }
                } else {
                    val toast = Toast.makeText(this@UpdatePostActivity, "Post tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@UpdatePostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }*/

 /*   private fun setViewPost(result : PostResponse) {
        val result = result.data as arrayOf()
        et_post_title.setText(result[1].toS)
//        et_post_body.setText(item!!.postBody)
//        et_post_image.setText(item!!.postImage)



    }*/

    fun insertPost(post_id : String, post_image : String) {
        PostNetworkConfig().getService().updatePost(
            et_pengguna_nama.text.toString().trim(),
            et_pengguna_email.text.toString().trim(),
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