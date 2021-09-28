package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.bansapp.R
import id.gits.si.bansapp.adapter.PostAPIAdapter
import id.gits.si.bansapp.model.DataItems
import id.gits.si.bansapp.model.DataPengguna
import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PenggunaNetworkConfig
import id.gits.si.bansapp.rest.PostNetworkConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_card_post.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("ok")
        getPosts()
        getUsernamePengguna(1)

        action_bar.setText("Beranda")

        btnTambah.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertPostActivity::class.java)
            startActivity(intent)
        }

    }
    
    private fun getPosts() {
        PostNetworkConfig().getService().getPost()
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>?,
                    response: Response<PostResponse>?
                ) {
                    rv_post.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv_post.adapter = PostAPIAdapter(response?.body()?.data as ArrayList<DataItems>)

                }

                override fun onFailure(call: Call<PostResponse>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun getUsernamePengguna(pengguna_id : Int) {
        PenggunaNetworkConfig().getService().getPenggunaID(pengguna_id)
            .enqueue(object : Callback<PenggunaResponse> {
                override fun onResponse(
                    call: Call<PenggunaResponse>?,
                    response: Response<PenggunaResponse>?
                ) {
                    //val data = response?.body()?.data as ArrayList<DataPengguna>
                    //tv_pengguna_username.setText(response?.body()?.message)

                }

                override fun onFailure(call: Call<PenggunaResponse>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }


}