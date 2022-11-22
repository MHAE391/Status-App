package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var option : Constants
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel(applicationContext,resources, "channelId", "Download")
        notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        custom_button.setOnClickListener {
            if(options_list.checkedRadioButtonId == -1){
                Toast.makeText(applicationContext,"Please Select Any Option",Toast.LENGTH_LONG).show()
            }else{
                val index = options_list.indexOfChild(findViewById(options_list.checkedRadioButtonId))
                option = when(index){
                    0 -> Constants.GLIDE
                    1 -> Constants.UDACITY
                    else -> Constants.RETROFIT
                }
               download(option.url)
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if(id == downloadID){
                val intent = Intent(applicationContext,DetailActivity::class.java)
                notificationManager.sendNotification(option.msg,applicationContext)
                intent.putExtra("appName",option.appName)
                intent.putExtra("appStatus","Success")
                startActivity(intent)
            }else {
                intent?.putExtra("appName",option.appName)
                intent?.putExtra("appStatus","Failed")
                startActivity(intent)
            }

        }
    }

    private fun download(URL : String) {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }


}
