package id.gits.si.bansapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import id.gits.si.bansapp.R
import id.gits.si.bansapp.activity.PushNotificationActivity

class AlarmReceiver : BroadcastReceiver() {
    private var alarmNotificationManager: NotificationManager? = null
    var NOTIFICATION_CHANNEL_ID = "bantolostd.my.id"
    var NOTIFICATION_CHANNEL_NAME = "bantolostd.my.id"
    private val NOTIFICATION_ID = 1

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(Intent.EXTRA_TITLE)!!
        val body = intent.getStringExtra(Intent.EXTRA_TEXT)!!
        playAudio(context)
        sendNotification(context, title, body)
    }

    private fun playAudio(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        mediaPlayer.start()
    }

    //handle notification
    private fun sendNotification(context: Context, title: String, body: String) {
        val notifTitle = title
        val notifContent = body
        alarmNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val newIntent = Intent(context, PushNotificationActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            newIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        //cek jika OS android Oreo atau lebih baru
        //kalau tidak di set maka notifikasi tidak akan muncul di OS tersebut
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance
            )
            alarmNotificationManager!!.createNotificationChannel(mChannel)
        }

        //Buat notification
        val inboxStyle = NotificationCompat.BigTextStyle().bigText(notifContent)
        val notifBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        notifBuilder.setContentTitle(notifTitle)
        notifBuilder.setSmallIcon(R.drawable.ic_notifications_active)
        notifBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        notifBuilder.setContentText(notifContent)
        notifBuilder.setAutoCancel(true)
        notifBuilder.setStyle(inboxStyle)
        notifBuilder.setContentIntent(contentIntent)
        //Tampilkan notifikasi
        alarmNotificationManager!!.notify(NOTIFICATION_ID, notifBuilder.build())
    }
}