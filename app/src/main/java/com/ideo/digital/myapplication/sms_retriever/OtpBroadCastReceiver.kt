package com.ideo.digital.myapplication.sms_retriever

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class OtpBroadCastReceiver(var resultCallback: ActivityResultLauncher<Intent?>) : BroadcastReceiver() {

//    fun setLauncher(callback: ActivityResultLauncher<Intent?>){
//        resultCallback = callback
//    }

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let { _ ->
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get consent intent
                        val consentIntent = extras?.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            resultCallback?.launch(consentIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle the exception ...
                            resultCallback?.launch(null)
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        // Time out occurred, handle the error.
                        resultCallback?.launch(null)
                    }
                }
            }
        }
    }
}