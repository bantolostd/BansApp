package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import kotlinx.android.synthetic.main.activity_insert_post.*
import kotlinx.android.synthetic.main.activity_insert_post.btn_insert
import kotlinx.android.synthetic.main.activity_insert_post.et_post_body
import kotlinx.android.synthetic.main.activity_insert_post.et_post_image
import kotlinx.android.synthetic.main.activity_insert_post.et_post_title
import kotlinx.android.synthetic.main.activity_update_post.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.action_bar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_insert_post)

        action_bar.setText("Tambah Post")
        btn_insert.setText("Tambah Post!")

        btnBack.setOnClickListener {
            val intent = Intent(this@InsertPostActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_insert.setOnClickListener {
            PostNetworkConfig().getService().insertPost(
                et_post_title.text.toString().trim(),
                et_post_body.text.toString().trim(),
                et_post_image.text.toString().trim(),"NOW()","1"
            ).enqueue(object: Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>?,
                    response: Response<PostResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body()?.status == 1){
                            val toast = Toast.makeText(this@InsertPostActivity, "User berhasil ditambahkan!", Toast.LENGTH_LONG)
                            toast.show()
                            goBackHome()
                        }
                    } else {
                        val toast = Toast.makeText(this@InsertPostActivity, "User gagal ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    val toast = Toast.makeText(this@InsertPostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
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