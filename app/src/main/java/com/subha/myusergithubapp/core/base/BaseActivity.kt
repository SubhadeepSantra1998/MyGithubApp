package com.subha.myusergithubapp.core.base

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.subha.myusergithubapp.presentation.view.ProgressDialog

open class BaseActivity : AppCompatActivity() {
    var mcontext: Context? = null
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mcontext = this
    }

    fun showProgress() {
        progressDialog.show()
    }


    fun closeProgress() {
        progressDialog.dismiss()
    }

    fun setIntent(activity: Class<*>) {
        startActivity(Intent(this, activity::class.java))
    }

    fun moveToFragment(itemId: Int, fragment: Fragment) {
        // replace fragment
        supportFragmentManager
            .beginTransaction()
            .replace(itemId, fragment)
            .addToBackStack("tag")
            .commit()
    }

    fun moveToFragmentAnim(itemId: Int, fragment: Fragment, anim1: Int) {
        supportFragmentManager
            .beginTransaction()
            //.setCustomAnimations(anim1, R.anim.fadeout, R.anim.out_right, R.anim.out_left)
            .replace(itemId, fragment)
            .addToBackStack("tag")
            .commit()
    }

    fun moveToFragmentMenu(itemId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            //.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.out_right, R.anim.out_left)
            .replace(itemId, fragment)
            .addToBackStack("tag")
            .commit()
    }


    fun customWindowmanager() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    fun showcommonDialog(): Dialog {
        val dialog = Dialog(this)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)


        return dialog
    }

}