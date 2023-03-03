package com.subha.myusergithubapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GithubApp : Application() {
    companion object {
        lateinit var instance: GithubApp
            private set
    }
}