package com.subha.myusergithubapp.core.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.subha.myusergithubapp.presentation.view.ProgressDialog

open class BaseBottomSheet : BottomSheetDialogFragment() {
    var mcontext: Context? = null
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mcontext = context

    }

    fun showProgress() {
        progressDialog.show()
    }

    fun closeProgress() {
        progressDialog.dismiss()
    }

    fun setIntent(activity: Class<*>) {
        startActivity(Intent(mcontext, activity::class.java))
    }


    //override fun getTheme(): Int = R.style.BottomSheetDialogTheme

}