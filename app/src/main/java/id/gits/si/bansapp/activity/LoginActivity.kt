package id.gits.si.bansapp.activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.Data
import id.gits.si.bansapp.model.LoginPenggunaResponse
import id.gits.si.bansapp.rest.PenggunaNetworkConfig
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnBack
import kotlinx.android.synthetic.main.activity_login.btn_submit
import kotlinx.android.synthetic.main.activity_login.tv_masuk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)

        btn_submit.setText("MASUK")

        btnBack.setOnClickListener {
            goBackWelcome()
        }

        btn_submit.setOnClickListener {
            loginPengguna()
        }

        tv_masuk.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get FCM token
            val token = task.result
            token?.let { Log.d(TAG, it) }
            //Toast.makeText(baseContext, "Token saat ini: $token", Toast.LENGTH_LONG).show()
            //Log.d(TAG, "Token saat ini: $token")
        })
    }

    fun loginPengguna() {
        PenggunaNetworkConfig().getService().loginPengguna(
            et_login_username.text.toString(),
            et_login_password.text.toString()
        ).enqueue(object: Callback<LoginPenggunaResponse> {
            override fun onResponse(
                call: Call<LoginPenggunaResponse>?,
                response: Response<LoginPenggunaResponse>?
            ) {
                if (response!!.isSuccessful){
                    val respon = response.body()!!
                    if(respon.status == 1) {
                        val data = respon.data!!
                        saveDataLogin(data)
                        goBackHome()
                    } else if(respon.status == 2){
                        val toast = Toast.makeText(this@LoginActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                        toast.show()
                    } else {
                        val toast = Toast.makeText(this@LoginActivity, respon.message, Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@LoginActivity, "Post tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<LoginPenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@LoginActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    private fun saveDataLogin(data: Data) {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("pengguna_id", data.penggunaId.toString())
        editor.putString("pengguna_username", data.penggunaUsername.toString())
        editor.apply()
    }

    fun goBackWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}