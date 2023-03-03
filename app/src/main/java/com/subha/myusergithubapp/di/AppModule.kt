package com.subha.myusergithubapp.di

import com.subha.myusergithubapp.core.util.Constants
import com.subha.myusergithubapp.data.remote.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun providesGithubAPI(retrofitBuilder: Retrofit.Builder): GithubApi {
        return retrofitBuilder.build().create(GithubApi::class.java)
    }

}