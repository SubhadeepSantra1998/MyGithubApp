package com.subha.myusergithubapp.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.subha.myusergithubapp.R


class ProgressDialog(context: Context) {

    private var alertDialog: AlertDialog = AlertDialog.Builder(context)
        .setCancelable(false)
        .setView(R.layout.activity_progressload).create()

    init {
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show() {
        if (!isShowing())
            alertDialog.show()
    }

    fun isShowing(): Boolean = alertDialog.isShowing

    fun dismiss() {
        if (isShowing())
            alertDialog.dismiss()
    }
}