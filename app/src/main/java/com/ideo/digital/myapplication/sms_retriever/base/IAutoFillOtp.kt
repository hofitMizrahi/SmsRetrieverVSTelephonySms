package com.ideo.digital.myapplication.sms_retriever.base

interface IAutoFillOtp{
    fun onOtpSmsReceived(sms : String)
}