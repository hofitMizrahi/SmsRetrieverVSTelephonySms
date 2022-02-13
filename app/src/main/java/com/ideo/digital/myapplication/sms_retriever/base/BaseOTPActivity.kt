package com.ideo.digital.myapplication.sms_retriever.base

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ideo.digital.myapplication.sms_retriever.otp_manager.SmsRetrieverManager

abstract class BaseOTPActivity : BaseActivity(), IAutoFillOtp {

    private var otpIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == RESULT_OK) {
            result.data?.let {
                val extraSmsMessage = it.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                extraSmsMessage?.isNotEmpty()?.let { notEmpty ->
                    if (notEmpty) {

                        Log.i("HofitTest", "BaseOTPActivity message = $notEmpty")

                        val otp2 = extraSmsMessage.substringAfter("הוא: ").substringBefore("\n")

                        val otp = extraSmsMessage.replace("[^0-9]".toRegex(), "")
                        onOtpSmsReceived(otp2)
                    }
                }
            }
        }
    }

    protected fun startObserveOtp(){
        Log.i("HofitTest", "BaseOTPActivity startObserveOtp()")
        SmsRetrieverManager.startSmsRetriever(this, otpIntentLauncher)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("HofitTest", "BaseOTPActivity onDestroy()")
        SmsRetrieverManager.unregister(this)
    }


}