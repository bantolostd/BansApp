package id.gits.si.bansapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.UserResponse
import id.gits.si.bansapp.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_insert_user.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_user.*
import kotlinx.android.synthetic.main.activity_update_user.btn_insert
import kotlinx.android.synthetic.main.activity_update_user.et_alamat
import kotlinx.android.synthetic.main.activity_update_user.et_email
import kotlinx.android.synthetic.main.activity_update_user.et_instagram
import kotlinx.android.synthetic.main.activity_update_user.et_nama
import kotlinx.android.synthetic.main.activity_update_user.et_no_hp
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.action_bar
import kotlinx.android.synthetic.main.toolbar_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        action_bar.setText("Update User")
        btn_insert.setText("Update Data!")

        val id_user = intent.getStringExtra("id_user")
        val nama = intent.getStringExtra("nama")
        val email = intent.getStringExtra("email")
        val no_hp = intent.getStringExtra("no_hp")
        val alamat = intent.getStringExtra("alamat")
        val instagram = intent.getStringExtra("instagram")

        et_nama.setText(nama)
        et_email.setText(email)
        et_no_hp.setText(no_hp)
        et_alamat.setText(alamat)
        et_instagram.setText(instagram)

        btnBack.setOnClickListener {
            val intent = Intent(this@UpdateUserActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btn_delete.setOnClickListener {
            RetrofitClient().getService().deleteUser(
                id_user.toString()).enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body()?.status == 1){
                            val toast = Toast.makeText(this@UpdateUserActivity, "User berhasil dihapus!", Toast.LENGTH_LONG)
                            toast.show()
                            goBackHome()
                        }
                    } else {
                        val toast = Toast.makeText(this@UpdateUserActivity, "User gagal dihapus!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    val toast = Toast.makeText(this@UpdateUserActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                    toast.show()
                }
            })
        }

        btn_insert.setOnClickListener {
            RetrofitClient().getService().updateUser(
                et_nama.text.toString().trim(),
                et_email.text.toString().trim(),
                et_no_hp.text.toString().trim(),
                et_alamat.text.toString().trim(),
                et_instagram.text.toString().trim(),
                id_user.toString()
                ).enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body()?.status == 1){
                            val toast = Toast.makeText(this@UpdateUserActivity, "User berhasil diperbaharui!", Toast.LENGTH_LONG)
                            toast.show()
                            goBackHome()
                        }
                    } else {
                        val toast = Toast.makeText(this@UpdateUserActivity, "User gagal diperbaharui!", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    val toast = Toast.makeText(this@UpdateUserActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                    toast.show()
                }
            })
        }

    }

    private fun getDataUserID(id: Int) {
        RetrofitClient().getService().getUserID(id)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>?,
                    response: Response<UserResponse>?
                ) {
                    if (response!!.isSuccessful){
                    val result = response.body()?.data
                    }else{
                        Toast.makeText(this@UpdateUserActivity, "Reponse Gagal", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                    Toast.makeText(this@UpdateUserActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}