package com.ideo.digital.myapplication.sms_retriever.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ideo.digital.myapplication.R
import com.ideo.digital.myapplication.sms_retriever.base.BaseActivity

class SendSmsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms)

        val btn: Button = findViewById(R.id.my_btn)
        btn.setOnClickListener {

            Log.i("HofitTest", "SendSmsActivity onCreate()")
            startActivity(Intent(this, AutoOtpActivity::class.java))
        }

    }


}