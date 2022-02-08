package com.ideo.digital.myapplication.broadcast_with_permissions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class OTPReceiver : BroadcastReceiver() {

    companion object{
        // must be static for background
        var unitFun : ((String) -> Unit)? = null
    }

    //can set the editText here

    fun setUnit(action : (String) -> Unit){
        unitFun = action
    }

    // OnReceive will keep trace when sms is been received in mobile
    override fun onReceive(context: Context, intent: Intent) {
        //message will be holding complete sms that is received

        CoroutineScope(Dispatchers.IO).launch{
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            var otp = ""
            messages?.let {
                for (sms in messages) {
                    val msg = sms.messageBody
                    // here we are spliting the sms using " : " symbol
                    otp = msg.replace("[^0-9]".toRegex(), "")
                }
                CoroutineScope(Main).launch {
                    unitFun?.invoke(otp)
                }
            }

            //unregister
        }
    }
}