package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.DetailPostResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import id.gits.si.bansapp.support.konversiTanggal
import id.gits.si.bansapp.support.lightStatusBar
import id.gits.si.bansapp.support.setFullScreen
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_update_post.*
import kotlinx.android.synthetic.main.item_card_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        setFullScreen(window)
        lightStatusBar(window, false)

        val post_id = intent.getStringExtra("post_id")

        getDetailPost(post_id.toString())

        btn_edit_detail_post.setOnClickListener {
            val intent = Intent(this, UpdatePostActivity::class.java)
            intent.putExtra("post_id", post_id)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            goBackHome()
        }

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
                    tv_detail_post_title.text = data.postTitle
                    tv_detail_post_body.text = data.postBody
                    tv_detail_post_time.text = konversiTanggal(data.postTime.toString())
                    val URL_FOTO = "http://192.168.100.125/gits_api/images/"
                    Glide.with(this@DetailPostActivity).load(URL_FOTO+data.postImage).centerCrop().into(iv_detail_post_image)
                } else {
                    val toast = Toast.makeText(this@DetailPostActivity, "Post tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<DetailPostResponse>, t: Throwable) {
                val toast = Toast.makeText(this@DetailPostActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}