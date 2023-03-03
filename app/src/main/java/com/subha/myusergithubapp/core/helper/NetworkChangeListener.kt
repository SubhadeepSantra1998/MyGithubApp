package com.subha.myusergithubapp.core.helper

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.subha.myusergithubapp.R


class NetworkChangeListener : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (!UitilsDefault.isNetworkAvailable(context)) {

            val view = View.inflate(context, R.layout.check_internet_dialog, null)
            var btnRetry: Button = view.findViewById(R.id.btn_retry)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.setCancelable(false)
            dialog.window?.setGravity(Gravity.CENTER)
            btnRetry.setOnClickListener {
                dialog.dismiss()
                onReceive(context, intent)
            }
        }
    }
}