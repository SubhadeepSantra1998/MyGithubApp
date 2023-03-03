package com.subha.myusergithubapp.presentation.add_repo_screen

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.example.couponapp.presentation.helper.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.core.base.BaseActivity
import com.subha.myusergithubapp.core.helper.NetworkChangeListener
import com.subha.myusergithubapp.databinding.ActivityCreateRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateRepositoryActivity : BaseActivity() {

    private val binding: ActivityCreateRepositoryBinding by viewBinding()
    val networkChangeListener = NetworkChangeListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repository)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.teal_700)
        setUI()
    }

    private fun setUI() {
        binding.toolbar.setNavigationIcon(com.subha.myusergithubapp.R.drawable.ic_baseline_arrow_back_24)

        binding.btnAdd.setOnClickListener {
            validateRepo()
        }
    }

    private fun validateRepo() {
        var name = binding.etName.text.toString().trim()
        var description = binding.etDescription.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            val snack =
                Snackbar.make(binding.root, "Repository name is empty", Snackbar.LENGTH_INDEFINITE)
            snack.setAction("DISMISS", View.OnClickListener {
                snack.dismiss()
            })
            snack.show()
        } else if (TextUtils.isEmpty(description)) {
            val snack =
                Snackbar.make(
                    binding.root,
                    "Repository description is empty",
                    Snackbar.LENGTH_INDEFINITE
                )
            snack.setAction("DISMISS", View.OnClickListener {
                snack.dismiss()
            })
            snack.show()
        } else {
            var bottom = AccessTokenBottomSheet(name, description)
            bottom.isCancelable = false
            bottom.show(supportFragmentManager, "popup")
        }
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


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}