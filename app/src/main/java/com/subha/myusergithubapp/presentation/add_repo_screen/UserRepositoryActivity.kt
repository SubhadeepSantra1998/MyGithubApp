package com.subha.myusergithubapp.presentation.add_repo_screen

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.couponapp.presentation.helper.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.core.base.BaseActivity
import com.subha.myusergithubapp.core.helper.NetworkChangeListener
import com.subha.myusergithubapp.core.helper.RecyclerViewItemDecoration
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.core.util.ApiEndpoints
import com.subha.myusergithubapp.core.util.Constants
import com.subha.myusergithubapp.databinding.ActivityAddRepoBinding
import com.subha.myusergithubapp.presentation.landing_screen.LandingScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserRepositoryActivity : BaseActivity(), RepositoryAdapter.clickListener {

    private val viewModel: LandingScreenViewModel by viewModels()
    private val binding: ActivityAddRepoBinding by viewBinding()

    lateinit var getRepoadapter: RepositoryAdapter
    val networkChangeListener = NetworkChangeListener()

    companion object {
        var username = " "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repo)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        setUI()
    }


    private fun setUI() {
        binding.fbAdd.setOnClickListener {
            startActivity(Intent(this, CreateRepositoryActivity::class.java))
        }
    }

    private fun initView() {
        if (intent != null) {
            username = intent.getStringExtra(Constants.USER)!!
            binding.tb.title = username

            githubRepoApiCall(username)
            userInfo(username)
        }
        binding.rvCard.apply {
            layoutManager = LinearLayoutManager(this@UserRepositoryActivity)
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
            getRepoadapter = RepositoryAdapter(context, this@UserRepositoryActivity)
            binding.rvCard.adapter = getRepoadapter
        }
    }

    private fun userInfo(query: String?) {

        viewModel.getUserInfo(ApiEndpoints.USERS + query)
        viewModel.userInfoLiveData.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    closeProgress()
                    binding.tvFollowers.text = it.data!!.followers.toString()
                    binding.tvFollowing.text = it.data!!.following.toString()
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
        })
    }

    private fun githubRepoApiCall(username: String) {
        viewModel.getUserRepoInfo(ApiEndpoints.USERS + username + "/repos")
        viewModel.userRepoInfoLiveData.observe(this, Observer {
            when (it) {
                is Resource.Success -> {
                    closeProgress()
                    if (it.data!!.isEmpty()) {
                        binding.llnodata.visibility = View.VISIBLE
                        binding.rvCard.visibility = View.GONE
                    } else {
                        binding.llnodata.visibility = View.GONE
                        binding.rvCard.visibility = View.VISIBLE
                        binding.tvRepository.text = it.data!!.size.toString()
                        it.data.forEach {

                            Glide.with(this)
                                .load(it.owner.avatar_url) // image url
                                .fitCenter()
                                .into(binding.imageUser)
                        }
                        getRepoadapter.setData(it.data!!)
                    }
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
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onStart() {
        var filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, filter)
        githubRepoApiCall(username)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }

    override fun enter(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun share(name: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = name
        val shareSub = name
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, name))
    }
}