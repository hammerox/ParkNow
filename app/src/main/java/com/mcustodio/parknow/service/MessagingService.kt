package com.mcustodio.parknow.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mcustodio.parknow.R
import com.mcustodio.parknow.view.SplashActivity


class MessagingService : FirebaseMessagingService() {


    private val CHANNEL_ID = "FIREBASE_ID"

    override fun onMessageReceived(message: RemoteMessage?) {
        super.onMessageReceived(message)
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_done)
                .setContentTitle(message?.notification?.title ?: "New message!")
                .setContentText(message?.notification?.body ?: "Here should be a message")
//                .setSound(alarmSound)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat.from(this).notify(0, notification)
    }
}