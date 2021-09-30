package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.bansapp.R
import id.gits.si.bansapp.adapter.PenggunaAPIAdapter
import id.gits.si.bansapp.model.DataPengguna
import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.rest.PenggunaNetworkConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_add
import kotlinx.android.synthetic.main.activity_pengguna_main.*
import kotlinx.android.synthetic.main.item_card_post.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenggunaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengguna_main)
        println("ok")
        getPengguna()
        //getUsernamePengguna(1)

        action_bar.setText("Daftar Pengguna")
        left_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_article))
        btn_add.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_add))
//        Glide.with(this)
//            .load(R.drawable.ic_article)
//            .into(left_icon)

        left_icon.setOnClickListener {
            val intent = Intent(this@PenggunaMainActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_add.setOnClickListener {
            val intent = Intent(this@PenggunaMainActivity, InsertPenggunaActivity::class.java)
            startActivity(intent)
        }


    }
    
    private fun getPengguna() {
        PenggunaNetworkConfig().getService().getPengguna()
            .enqueue(object : Callback<PenggunaResponse> {
                override fun onResponse(
                    call: Call<PenggunaResponse>?,
                    response: Response<PenggunaResponse>?
                ) {
                    rv_pengguna.layoutManager = LinearLayoutManager(this@PenggunaMainActivity)
                    rv_pengguna.adapter = PenggunaAPIAdapter(response?.body()?.data as ArrayList<DataPengguna>)

                }

                override fun onFailure(call: Call<PenggunaResponse>?, t: Throwable?) {
                    Toast.makeText(this@PenggunaMainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this@PenggunaMainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }


}