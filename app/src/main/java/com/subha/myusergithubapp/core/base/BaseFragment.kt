package com.subha.myusergithubapp.core.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.subha.myusergithubapp.presentation.view.ProgressDialog

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    lateinit var mcontext: Context
    lateinit var fragmentActivity: FragmentActivity
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mcontext = requireContext()
        fragmentActivity = requireActivity()
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

    /*fun moveToFragment(itemId: Int, fragment: Fragment) {
        // replace fragment
        childFragmentManager
            .beginTransaction()
            .replace(itemId, fragment)
            .addToBackStack("tag")
            .commit()
    }*/

    protected fun moveToFragment(
        container: Int,
        fragment: Fragment,


        ) {

        fragmentActivity.supportFragmentManager
            .beginTransaction()
            .replace(container, fragment)
            .addToBackStack("tag")
            .commit()

    }

    protected fun moveToFragmentAnim(
        container: Int,
        fragment: Fragment,


        ) {

        fragmentActivity.supportFragmentManager
            .beginTransaction()
            //.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.out_right, R.anim.out_left)
            .replace(container, fragment)
            .addToBackStack("tag")
            .commit()

    }
}