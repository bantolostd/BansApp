package id.gits.si.bansapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.bansapp.R
import id.gits.si.bansapp.adapter.PostAPIAdapter
import id.gits.si.bansapp.model.DataItems
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PostNetworkConfig
import id.gits.si.bansapp.support.cekLogin
import id.gits.si.bansapp.support.goBackHome
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_card_post.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.Settings

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLogin(pengguna_id, this)

        getPosts()

        action_bar.setText("Beranda")

        val deviceID: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        left_icon.setOnClickListener {
            val intent = Intent(this@MainActivity, PenggunaMainActivity::class.java)
            startActivity(intent)
        }

        btn_add.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertPostActivity::class.java)
            startActivity(intent)
        }

        right_icon.setOnClickListener {
//            logout()
            /*val intent = Intent(this@MainActivity, PushNotificationActivity::class.java)
            startActivity(intent)*/
            Toast.makeText(this@MainActivity, "Device ID : $deviceID", Toast.LENGTH_LONG).show()
        }




    }

    private fun logout() {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        goBackHome(this)
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



}