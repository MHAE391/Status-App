package com.udacity

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0

@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val action = NotificationCompat.Action.Builder(0,"Check the status",contentPendingIntent).build()

    val builder = NotificationCompat.Builder(
        applicationContext,
        "channelId"
    )
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .addAction(action)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
