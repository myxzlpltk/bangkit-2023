package com.dicoding.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle == null) return

            val pdusObj = bundle.get("pdus") as Array<*>
            for (aPdusObj in pdusObj) {
                val currentMessage = getIncomingMessage(aPdusObj as ByteArray, bundle)
                val senderNum = currentMessage.displayOriginatingAddress
                val message = currentMessage.displayMessageBody
                Log.d(TAG, "sender: $senderNum\nmessage: $message")

                val showSmsIntent = Intent(context, SmsReceiverActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                    putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message)
                }
                context.startActivity(showSmsIntent)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception smsReceiver $e")
        }
    }

    private fun getIncomingMessage(args: ByteArray, bundle: Bundle): SmsMessage {
        val format = bundle.getString("format")
        return SmsMessage.createFromPdu(args, format)
    }
}