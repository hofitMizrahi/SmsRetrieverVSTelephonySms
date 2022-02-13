package com.ideo.digital.myapplication.sms_retriever

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ideo.digital.myapplication.R

class MainActivitySmsRetrieve : AppCompatActivity() {

    var editText: EditText? = null

    private var otpIntentLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result != null && result.resultCode == RESULT_OK) {
            result.data?.let {
                val extraSmsMessage = it.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                extraSmsMessage?.isNotEmpty()?.let { notEmpty ->
                    if (notEmpty) {
                        val otp = extraSmsMessage.replace("[^0-9]".toRegex(), "")
                        onOtpSmsReceived(otp)
                    }
                }
            }
        }
    }

    private fun onOtpSmsReceived(extraSmsMessage: String) {
        editText?.setText(extraSmsMessage)
    }

    override fun onStart() {
        super.onStart()
        SmsRetrieverManager.startSmsRetriever(this, otpIntentLauncher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sms_retrive)

        val btn: Button = findViewById(R.id.my_btn)
        editText = findViewById(R.id.my_edit_text)
        btn.setOnClickListener {
            SmsRetrieverManager.startSmsRetriever(this, otpIntentLauncher)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SmsRetrieverManager.unregister(this)
    }
}