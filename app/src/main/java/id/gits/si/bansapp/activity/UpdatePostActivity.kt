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
        btn_insert.setText("Update Post!")
        btn_delete.setText("Hapus Post!")

        val post_id = intent.getStringExtra("post_id")
        val post_title = intent.getStringExtra("post_title")
        val post_body = intent.getStringExtra("post_body")
        val post_image = intent.getStringExtra("post_image")
//        val post_time = intent.getStringExtra("post_time")
//        val post_credit = intent.getStringExtra("post_credit")

        et_post_title.setText(post_title)
        et_post_body.setText(post_body)
        et_post_image.setText(post_image)

        btnBack.setOnClickListener {
            val intent = Intent(this@UpdatePostActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_delete.setOnClickListener {
            PostNetworkConfig().getService().deletePost(
                post_id.toString()).enqueue(object: Callback<PostResponse> {
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

        btn_insert.setOnClickListener {
            PostNetworkConfig().getService().updatePost(
                et_post_title.text.toString().trim(),
                et_post_body.text.toString().trim(),
                et_post_image.text.toString().trim(),
                post_id.toString()
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

    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}