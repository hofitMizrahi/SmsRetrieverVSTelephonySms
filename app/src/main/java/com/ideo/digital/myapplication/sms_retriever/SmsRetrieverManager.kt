package com.ideo.digital.myapplication.sms_retriever

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.phone.SmsRetriever


object SmsRetrieverManager {

    private var smsReceiver : OtpBroadCastReceiver?  = null

    fun startSmsRetriever(activity: Activity, resultCallback : ActivityResultLauncher<Intent?>){

        val client = SmsRetriever.getClient(activity)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener { aVoid: Void? ->

//            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//            smsReceiver = OtpBroadCastReceiver(resultCallback)
//            activity.registerReceiver(smsReceiver, intentFilter)
        }
        task.addOnFailureListener { e: Exception? ->
            //error
            Log.i("HofitTest", "addOnFailureListener")
        }
    }
}