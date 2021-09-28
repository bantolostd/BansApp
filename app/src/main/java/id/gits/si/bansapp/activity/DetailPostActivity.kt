package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.gits.si.bansapp.R
import id.gits.si.bansapp.support.konversiTanggal
import id.gits.si.bansapp.support.lightStatusBar
import id.gits.si.bansapp.support.setFullScreen
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_update_post.*
import kotlinx.android.synthetic.main.item_card_post.*

class DetailPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        setFullScreen(window)
        lightStatusBar(window, false)

        val post_id = intent.getStringExtra("post_id")
        val post_title = intent.getStringExtra("post_title")
        val post_body = intent.getStringExtra("post_body")
        val post_image = intent.getStringExtra("post_image")
        val post_time = intent.getStringExtra("post_time")
        val post_credit = intent.getStringExtra("post_credit")

        tv_detail_post_title.setText(post_title)
        tv_detail_post_body.setText(post_body)
        tv_detail_post_time.setText(konversiTanggal(post_time.toString()))
        //et_post_image.setText(post_image)

        btn_edit_detail_post.setOnClickListener {
            val intent = Intent(this, UpdatePostActivity::class.java)
            intent.putExtra("post_id", post_id)
            intent.putExtra("post_title", post_title)
            intent.putExtra("post_body", post_body)
            intent.putExtra("post_image", post_image)
            intent.putExtra("post_time", post_time)
            intent.putExtra("post_credit", post_credit)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this@DetailPostActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}