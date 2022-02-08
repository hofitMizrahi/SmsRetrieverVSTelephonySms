package com.ideo.digital.myapplication.sms_retriever

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ideo.digital.myapplication.R

class MainActivitySmsRetrieve : AppCompatActivity() {

    var editText : EditText? = null

    private var otpIntentLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result != null && result.resultCode == RESULT_OK) {
            result.data?.let {
                val extraSmsMessage = it.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                extraSmsMessage?.isNotEmpty()?.let { notEmpty ->
                    if(notEmpty) {
                        onOtpSmsReceived(extraSmsMessage)
                    }
                }
            }
        }
    }

    private fun onOtpSmsReceived(extraSmsMessage: String) {
        editText?.setText(extraSmsMessage)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sms_retrive)

        val btn : Button = findViewById(R.id.my_btn)
        editText = findViewById(R.id.my_edit_text)
        btn.setOnClickListener {
            SmsRetrieverManager.startSmsRetriever(this, otpIntentLauncher)
        }

        val task = SmsRetriever.getClient(this).startSmsUserConsent(null)

        task.addOnSuccessListener {
            register()
        }
        task.addOnFailureListener {
            //error
            Log.i("HofitTest", "addOnFailureListener")
        }

    }

    private fun register() {

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        val receiver = OtpBroadCastReceiver(otpIntentLauncher)
        this.registerReceiver(receiver, intentFilter)
    }
}