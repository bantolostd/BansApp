package id.gits.si.bansapp.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import id.gits.si.bansapp.R
import id.gits.si.bansapp.notification.AlarmReceiver
import id.gits.si.bansapp.support.cekLogin
import kotlinx.android.synthetic.main.activity_alarm_manager.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.action_bar
import kotlinx.android.synthetic.main.toolbar_detail.*
import java.util.*


class AlarmManagerActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private var selectedImage: Uri? = null

    private lateinit var tvAlarmPrompt: TextView
    private var ALARM_REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager)

        // cek login
        sharedPreferences = getSharedPreferences("DATA_LOGIN", Context.MODE_PRIVATE)
        val pengguna_id = sharedPreferences.getString("pengguna_id", "").toString()
        cekLogin(pengguna_id, this@AlarmManagerActivity)

        action_bar.setText("Alarm Manager")

        init()
    }

    private fun init() {
        tvAlarmPrompt = findViewById(R.id.tv_alarm_prompt)
    }

    fun clickSetAlarm(view: View) {
        tvAlarmPrompt.text = ""
        openTimePickerDialog()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            val calNow = Calendar.getInstance()

            val calSet = Calendar.getInstance()
            calSet.apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            when {
                calSet <= calNow -> {
                    // Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1)
                    Log.i("hasil", " =<0")
                }
                calSet > calNow -> {
                    Log.i("hasil", " > 0")
                }
                else -> {
                    Log.i("hasil", " else ")
                }
            }
            setAlarm(calSet)
        }
        val timePickerDialog = TimePickerDialog(this,timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),true)
        timePickerDialog.setTitle("Atur waktu alarm")
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private fun setAlarm(targetCal: Calendar) {
        tvAlarmPrompt.text = "Alarm berhasil diatur pada ${targetCal.time}".trimIndent()
        val intent = Intent(this@AlarmManagerActivity, AlarmReceiver::class.java).apply {
            putExtra(Intent.EXTRA_TITLE, et_post_title.text.toString())
            putExtra(Intent.EXTRA_TEXT, et_post_body.text.toString())
        }
        val pendingIntent = PendingIntent.getBroadcast(this@AlarmManagerActivity, ALARM_REQUEST_CODE, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetCal.timeInMillis,
            pendingIntent
        )
    }
}