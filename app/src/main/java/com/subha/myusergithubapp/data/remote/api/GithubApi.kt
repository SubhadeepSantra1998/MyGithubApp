package com.subha.myusergithubapp.data.remote.api

import com.subha.myusergithubapp.core.util.ApiEndpoints
import com.subha.myusergithubapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {

    @GET()
    suspend fun user(@Url url: String): Response<UserRes>


    @GET(ApiEndpoints.SEARCHUSER)
    suspend fun searchUsername(@Query("q") q: String): Response<SearchUserRes>

    @GET()
    suspend fun userRepo(@Url url: String): Response<ArrayList<UserRepoResItem>>

    @POST(ApiEndpoints.CREATEREPO)
    suspend fun createRepo(
        @Header("Authorization") token: String,
        @Body createRepoRequest: CreateRepoRequest
    ): Response<CreateRepoRes>
}