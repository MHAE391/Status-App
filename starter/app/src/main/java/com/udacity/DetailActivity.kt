package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*


class DetailActivity : AppCompatActivity() {
    private var fileName = ""
    private var status = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        val intent = intent
         fileName = intent.getStringExtra("appName").toString()
         status = intent.getStringExtra("appStatus").toString()
        file_name_ans .text = fileName
        download_status.text = status
    }

}
