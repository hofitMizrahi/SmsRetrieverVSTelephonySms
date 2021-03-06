package com.ideo.digital.myapplication.sms_retriever.otp_manager

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ideo.digital.myapplication.sms_retriever.broadcast.OtpBroadCastReceiver


object SmsRetrieverManager {

    private var smsReceiver : OtpBroadCastReceiver?  = null

    fun startSmsRetriever(activity: Activity, resultCallback : ActivityResultLauncher<Intent?>){

        val task = SmsRetriever.getClient(activity).startSmsUserConsent(null)

        task.addOnSuccessListener {

            Log.i("HofitTest", "addOnSuccessListener")

            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            smsReceiver = OtpBroadCastReceiver(resultCallback)
            activity.registerReceiver(smsReceiver, intentFilter)
        }
        task.addOnFailureListener {
            //error
            Log.i("HofitTest", "addOnFailureListener = exception: $it")
        }

    }

    fun unregister(activity: Activity) {
        Log.i("HofitTest", "unregisterReceiver")
        activity.unregisterReceiver(smsReceiver)
    }
}