package com.subha.myusergithubapp.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import com.example.couponapp.presentation.helper.viewBinding
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.core.base.BaseActivity
import com.subha.myusergithubapp.databinding.ActivitySplashScreenBinding
import com.subha.myusergithubapp.presentation.landing_screen.LandingScreenActivity

class SplashScreenActivity : BaseActivity() {

    private val binding: ActivitySplashScreenBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = ContextCompat.getColor(this, R.color.splash_blue)


        binding.motionLayout.startLayoutAnimation()
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                startActivity(Intent(this@SplashScreenActivity, LandingScreenActivity::class.java))
                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })

    }
}