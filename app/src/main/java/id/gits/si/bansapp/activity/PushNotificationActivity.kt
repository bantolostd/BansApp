package id.gits.si.bansapp.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import id.gits.si.bansapp.R
import id.gits.si.bansapp.model.PushNotificationResponse
import id.gits.si.bansapp.rest.NetworkConfig
import id.gits.si.bansapp.support.cekLogin
import kotlinx.android.synthetic.main.activity_insert_pengguna.*
import kotlinx.android.synthetic.main.activity_insert_post.*
import kotlinx.android.synthetic.main.activity_push_notification.*
import kotlinx.android.synthetic.main.activity_push_notification.btn_insert
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.action_bar
import kotlinx.android.synthetic.main.toolbar_detail.*
import java.time.LocalDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PushNotificationActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_push_notification)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLogin(pengguna_id, this@PushNotificationActivity)

        action_bar.setText("Push Notification")
        btn_insert.setText("Push Notification")

        btn_insert.setOnClickListener {
            val title = et_notification_title.text.toString()
            val message = et_notification_body.text.toString()
            val recipientToken = et_notification_token.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                kirimNotif()
            } else {
                val toast = Toast.makeText(this@PushNotificationActivity, "Masih ada data yang kosong!", Toast.LENGTH_LONG)
                toast.show()
            }
//            kirimNotif()
        }

        btnBack.setOnClickListener {
            goBackHome()
        }

        et_notification_token.setVisibility(View.GONE)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get FCM token
            val token = task.result
            token?.let { Log.d(ContentValues.TAG, it) }
            et_notification_token.setText("$token")
        })

    }

    private fun kirimNotif() {
        NetworkConfig().getService().pushNotification(
            et_notification_title.text.toString(),
            et_notification_body.text.toString(),
            et_notification_token.text.toString()
        ).enqueue(object: Callback<PushNotificationResponse> {
            override fun onResponse(
                call: Call<PushNotificationResponse>?,
                response: Response<PushNotificationResponse>?
            ) {
                if (response!!.isSuccessful){
                    val toast = Toast.makeText(this@PushNotificationActivity, "Notifikasi berhasil terkirim!", Toast.LENGTH_LONG)
                    toast.show()
                } else {
                    val toast = Toast.makeText(this@PushNotificationActivity, "Notifikasi gagal terkirim!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<PushNotificationResponse>, t: Throwable) {
                val toast = Toast.makeText(this@PushNotificationActivity, "Tidak ada respon $t", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWaktuSekarang(): LocalDateTime? {
        val sekarang = LocalDateTime.now()
        return sekarang
    }

    fun goBackHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}