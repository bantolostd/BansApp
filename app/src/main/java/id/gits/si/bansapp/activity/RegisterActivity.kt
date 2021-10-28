package id.gits.si.bansapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.Data
import id.gits.si.bansapp.model.LoginPenggunaResponse
import id.gits.si.bansapp.model.PenggunaResponse
import id.gits.si.bansapp.rest.NetworkConfig
import id.gits.si.bansapp.support.goBackHome
import kotlinx.android.synthetic.main.activity_insert_pengguna.*
import kotlinx.android.synthetic.main.activity_insert_post.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btnBack
import kotlinx.android.synthetic.main.activity_register.btn_submit
import kotlinx.android.synthetic.main.activity_register.et_register_email
import kotlinx.android.synthetic.main.activity_register.et_register_nama
import kotlinx.android.synthetic.main.activity_register.tv_masuk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)

        btn_submit.setText("DAFTAR")

        btnBack.setOnClickListener {
            goBackWelcome()
        }

        tv_masuk.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_submit.setOnClickListener {
            insertPengguna()
        }
    }

    private fun insertPengguna(post_image : String = "user_default.jpg") {
        NetworkConfig().getPenggunaService().insertPengguna(
            et_register_nama.text.toString().trim(),
            et_register_email.text.toString().trim(),
            et_register_username.text.toString().trim(),
            et_register_password.text.toString().trim(),
            post_image
        ).enqueue(object: Callback<PenggunaResponse> {
            override fun onResponse(
                call: Call<PenggunaResponse>?,
                response: Response<PenggunaResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        val toast = Toast.makeText(this@RegisterActivity, "Berhasil mendaftarkan akun!", Toast.LENGTH_LONG)
                        toast.show()
                        loginPengguna()
                    } else if(response.body()?.status == 2){
                        val toast = Toast.makeText(this@RegisterActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@RegisterActivity, "Gagal mendaftarkan akun!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@RegisterActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    fun loginPengguna() {
        NetworkConfig().getPenggunaService().loginPengguna(
            et_register_username.text.toString(),
            et_register_password.text.toString()
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
                        val toast = Toast.makeText(this@RegisterActivity, "Selamat datang " + data.penggunaUsername + "!", Toast.LENGTH_LONG)
                        toast.show()
                        goBackHome(this@RegisterActivity)
                    } else if(respon.status == 2){
                        val toast = Toast.makeText(this@RegisterActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                        toast.show()
                    } else {
                        val toast = Toast.makeText(this@RegisterActivity, respon.message, Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@RegisterActivity, "Post tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<LoginPenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@RegisterActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
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

}