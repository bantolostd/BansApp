package id.gits.si.bansapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.bansapp.R
import id.gits.si.bansapp.adapter.PenggunaAPIAdapter
import id.gits.si.bansapp.model.DataPengguna
import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.rest.NetworkConfig
import id.gits.si.bansapp.support.cekLogin
import id.gits.si.bansapp.support.goBackHome
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_add
import kotlinx.android.synthetic.main.activity_pengguna_main.*
import kotlinx.android.synthetic.main.item_card_post.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenggunaMainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengguna_main)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLogin(pengguna_id, this)

        getPengguna()

        action_bar.setText("Daftar Pengguna")
        left_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_article))
        btn_add.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_add))
        right_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout))
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

        right_icon.setOnClickListener {
            logout()
        }


    }

    private fun logout() {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        Handler().postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 1000)
    }
    
    private fun getPengguna() {
        NetworkConfig().getPenggunaService().getPengguna()
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


}