package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.gits.si.bansapp.R
import id.gits.si.bansapp.adapter.UserAPIAdapter
import id.gits.si.bansapp.model.DataItem
import id.gits.si.bansapp.model.UserResponse
import id.gits.si.bansapp.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_card_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataUser()

        btnTambah.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertUserActivity::class.java)
            startActivity(intent)
        }

    }
    private fun getDataUser() {
        RetrofitClient().getService().getUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    rv_user.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv_user.adapter = UserAPIAdapter(response?.body()?.data as ArrayList<DataItem>)

                }

                override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }

}