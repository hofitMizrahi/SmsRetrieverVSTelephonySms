package com.ideo.digital.myapplication.broadcast_with_permissions

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ideo.digital.myapplication.R

class MainActivity : AppCompatActivity() {

    private var editText : EditText? = null
    private var broadcast : OTPReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn : Button = findViewById(R.id.my_btn)
        editText = findViewById(R.id.my_edit_text)
        btn.setOnClickListener {
            requestSmsPermission()
        }
    }

    private fun requestSmsPermission() {
        val smsPermission: String = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, smsPermission)

        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permissionList = arrayOfNulls<String>(1)
            permissionList[0] = smsPermission
            ActivityCompat.requestPermissions(this, permissionList, 1)
        }else {

            broadcast = OTPReceiver()
            broadcast?.setUnit{
                editText?.setText(it)
            }
            val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION).apply {
                addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            }
            registerReceiver(broadcast, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        broadcast?.let {
            unregisterReceiver(it)
        }
    }
}

