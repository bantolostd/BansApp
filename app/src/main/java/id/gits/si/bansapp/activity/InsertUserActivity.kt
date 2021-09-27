package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.UserResponse
import id.gits.si.bansapp.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_insert_user.*
import kotlinx.android.synthetic.main.toolbar_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_insert_user)

        btnBack.setOnClickListener {
            val intent = Intent(this@InsertUserActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_insert.setOnClickListener {
            RetrofitClient().getService().insertUser(
                et_nama.text.toString().trim(),
                et_email.text.toString().trim(),
                et_no_hp.text.toString().trim(),
                et_alamat.text.toString().trim(),
                et_instagram.text.toString().trim()
            ).enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body()?.status == 1){
                            val toast = Toast.makeText(this@InsertUserActivity, "User berhasil ditambahkan!", Toast.LENGTH_LONG)
                            toast.show()
                            goBackHome()
                        }
                    } else {
                        val toast = Toast.makeText(this@InsertUserActivity, "User gagal ditambahkan!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    val toast = Toast.makeText(this@InsertUserActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                    toast.show()
                }
            })
        }

    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}