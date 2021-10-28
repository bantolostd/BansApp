package id.gits.si.bansapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import id.gits.si.bansapp.R
import id.gits.si.bansapp.support.cekLogin
import id.gits.si.bansapp.support.cekLoginAwal
import kotlinx.android.synthetic.main.activity_welcome.*
import android.content.DialogInterface




class WelcomeActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLoginAwal(pengguna_id, this@WelcomeActivity)

        btn_login.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

}