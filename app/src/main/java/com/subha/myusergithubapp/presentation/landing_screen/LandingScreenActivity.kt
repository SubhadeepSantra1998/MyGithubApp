package com.subha.myusergithubapp.presentation.landing_screen

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.couponapp.presentation.helper.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.core.base.BaseActivity
import com.subha.myusergithubapp.core.helper.NetworkChangeListener
import com.subha.myusergithubapp.core.helper.RecyclerViewItemDecoration
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.core.util.Constants
import com.subha.myusergithubapp.data.models.Item
import com.subha.myusergithubapp.databinding.ActivityLandingScreenBinding
import com.subha.myusergithubapp.presentation.add_repo_screen.UserRepositoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingScreenActivity : BaseActivity(), LandingScreenAdapter.clickListener {

    private val viewModel: LandingScreenViewModel by viewModels()
    private val binding: ActivityLandingScreenBinding by viewBinding()

    lateinit var landingScreenadapter: LandingScreenAdapter
    var userList = ArrayList<Item>()


    val networkChangeListener = NetworkChangeListener()
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)
        initview()
        setUI()
    }

    private fun initview() {
        Toast.makeText(this, "Developed by Subhadeep Santra", Toast.LENGTH_SHORT).show()
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(this@LandingScreenActivity)
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
            landingScreenadapter = LandingScreenAdapter(context, this@LandingScreenActivity)
            binding.rvListUser.adapter = landingScreenadapter
        }
    }

    private fun setUI() {

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                searchUsername(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


    private fun searchUsername(query: String?) {

        viewModel.searchUserName(query)
        viewModel.searchuserLiveData.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    closeProgress()
                    userList.clear()
                    userList.addAll(it.data!!.items)
                }
                is Resource.Error -> {
                    closeProgress()
                    val snack = Snackbar.make(
                        binding.root,
                        it.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snack.setAction("DISMISS", View.OnClickListener {
                        snack.dismiss()
                    })
                    snack.show()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
            if (userList.isEmpty()) {
                binding.llnodata.visibility = View.VISIBLE
                binding.rvListUser.visibility = View.GONE
            } else {
                binding.llnodata.visibility = View.GONE
                binding.rvListUser.visibility = View.VISIBLE
                landingScreenadapter.setData(userList)
            }

        })

    }

    override fun enter(name: String) {
        startActivity(
            Intent(this, UserRepositoryActivity::class.java)
                .putExtra(Constants.USER, name)
        )
    }

    override fun onStart() {
        var filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if (doubleBackToExitPressedOnce && count == 0) {
            super.onBackPressed()
            return
        } else {
            supportFragmentManager.popBackStack()
        }
        this.doubleBackToExitPressedOnce = true
        val toast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}