package com.ideo.digital.myapplication.sms_retriever.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ideo.digital.myapplication.R
import com.ideo.digital.myapplication.sms_retriever.base.BaseOTPActivity

class AutoOtpActivity : BaseOTPActivity() {

    var editText: EditText? = null

    override fun onOtpSmsReceived(sms: String) {
        editText?.setText(sms)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sms_retrive)

        Log.i("HofitTest", "MainActivitySmsRetrieve onCreate()")

        editText = findViewById(R.id.my_edit_text)

        startObserveOtp()
    }
}