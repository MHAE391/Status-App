package com.udacity

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build

enum class Constants (val url: String, val appName: String, val msg:String){
        GLIDE(
            "https://github.com/bumptech/glide/archive/master.zip",
            "Glide: Image Loading Library By BumpTech",
            "Glide repository has been downloaded"
        ),
        UDACITY(
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip",
            "Udacity: Android Kotlin Nanodegree",
            "Udacity's third project repository has been downloaded"
        ),
        RETROFIT(
            "https://github.com/square/retrofit/archive/master.zip",
            "Retrofit: Type-safe HTTP client by Square, Inc",
            "Retrofit repository has been downloaded"
        )
}
fun createChannel( applicationContext : Context , resources: Resources,channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = resources.getString(R.string.notification_description)
        val notificationManager = applicationContext.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
