package id.gits.si.bansapp.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.gits.si.bansapp.R
import id.gits.si.bansapp.activity.LoginActivity
import id.gits.si.bansapp.activity.PushNotificationActivity
import kotlin.random.Random

const val channelId = "notification_channel"
const val channelName = "id.gits.si.bansapp"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    // Token: cILai7ytT4CaACmyNm508i:APA91bEVnhyDIsTPeMwScT2tRZWDeNsQpi58B-t40OOOCU7I8_7brct0GZu91O2dUKUP7lO5IgYqoRjBdgi5RIrBASibYMPBpuBzFGVGj-lSXklc9o0LMU-WNC8KNwFqquKyuwiUYin1

    override fun onNewToken(token: String) {
        Log.d("TAG", "The token refreshed: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null){
            super.onMessageReceived(remoteMessage)
            val intent = Intent(this, PushNotificationActivity::class.java)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                createNotificationChannel(notificationManager)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,FLAG_ONE_SHOT)
            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setSmallIcon(R.drawable.ic_notifications_active)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()
            notificationManager.notify(notificationID,notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(channelId, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

}