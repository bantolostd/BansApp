package id.gits.si.bansapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.Data
import id.gits.si.bansapp.model.LoginPenggunaResponse
import id.gits.si.bansapp.model.PostResponse
import id.gits.si.bansapp.rest.PenggunaNetworkConfig
import id.gits.si.bansapp.rest.PostNetworkConfig
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnBack
import kotlinx.android.synthetic.main.activity_login.btn_submit
import kotlinx.android.synthetic.main.activity_login.tv_masuk
import kotlinx.android.synthetic.main.activity_update_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var deviceId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)

        deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        // Init Biometric
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this,executor, object:BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@LoginActivity,"Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Jika auth berhasil/sukses, maka cek apakah fingerprint/device id tersebut sudah terdaftar dalam database atau belum
                loginByBiometric(deviceId)
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@LoginActivity,"Authentication failed!", Toast.LENGTH_SHORT).show()
            }
        })

        // Set tulisan login biometric
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Masuk Ban's App dengan biometric")
            .setSubtitle("Masuk dengan sidik jari Anda")
            .setNegativeButtonText("Gunakan password akun")
            .build()



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

        btn_biometric.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }

    fun updatePenggunaHardware(pengguna_id : String, pengguna_hardware_id : String) {
        PenggunaNetworkConfig().getService().updatePenggunaHardware(
            pengguna_id,
            pengguna_hardware_id
        ).enqueue(object: Callback<LoginPenggunaResponse> {
            override fun onResponse(
                call: Call<LoginPenggunaResponse>?,
                response: Response<LoginPenggunaResponse>?
            ) {
                if (response!!.isSuccessful){
                    if (response.body()?.status == 1){
                        //val toast = Toast.makeText(this@LoginActivity, "Device ID berhasil diperbaharui! $pengguna_id $pengguna_hardware_id $msg", Toast.LENGTH_LONG)
                        //toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@LoginActivity, "Device ID gagal diperbaharui!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<LoginPenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@LoginActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
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
                        updatePenggunaHardware(data.penggunaId.toString(), deviceId)
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
                    val toast = Toast.makeText(this@LoginActivity, "Pengguna tidak ditemukan!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<LoginPenggunaResponse>, t: Throwable) {
                val toast = Toast.makeText(this@LoginActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    // Login menggunakan fingerprint/biometric
    private fun loginByBiometric(pengguna_hardware_id: String) {
        PenggunaNetworkConfig().getService().loginByDevice(
            pengguna_hardware_id
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
                    } else {
                        val toast = Toast.makeText(this@LoginActivity, respon.message, Toast.LENGTH_LONG)
                        toast.show()
                    }
                } else {
                    val toast = Toast.makeText(this@LoginActivity, "Pengguna tidak ditemukan!", Toast.LENGTH_LONG)
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