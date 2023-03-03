package com.subha.myusergithubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.data.models.*
import com.subha.myusergithubapp.data.remote.api.GithubApi
import org.json.JSONObject
import javax.inject.Inject

class GithubRepo @Inject constructor(private val api: GithubApi) {

    private val _userInfoLiveData = MutableLiveData<Resource<UserRes>>()
    val userInfoLiveData: LiveData<Resource<UserRes>>
        get() = _userInfoLiveData


    private val _userRepoInfoLiveData = MutableLiveData<Resource<ArrayList<UserRepoResItem>>>()
    val userRepoInfoLiveData: LiveData<Resource<ArrayList<UserRepoResItem>>>
        get() = _userRepoInfoLiveData


    private val _createRepoLiveData = MutableLiveData<Resource<CreateRepoRes>>()
    val createRepoLiveData: LiveData<Resource<CreateRepoRes>>
        get() = _createRepoLiveData


    private val _searchUsernameInfoLiveData = MutableLiveData<Resource<SearchUserRes>>()
    val searchUsernameInfoLiveData: LiveData<Resource<SearchUserRes>>
        get() = _searchUsernameInfoLiveData


    suspend fun getuserinfo(url: String) {
        _userInfoLiveData.postValue(Resource.Loading())
        val response = api.user(url)
        if (response.isSuccessful && response.body() != null) {
            _userInfoLiveData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userInfoLiveData.postValue(Resource.Error(errorObj.getString("message")))
        } else {
            _userInfoLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }

    suspend fun getUserRepoInfo(url: String) {
        _userRepoInfoLiveData.postValue(Resource.Loading())
        val response = api.userRepo(url)
        if (response.isSuccessful && response.body() != null) {
            _userRepoInfoLiveData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userRepoInfoLiveData.postValue(Resource.Error(errorObj.getString("message")))
        } else {
            _userRepoInfoLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }


    suspend fun createRepo(token: String, request: CreateRepoRequest) {
        _createRepoLiveData.postValue(Resource.Loading())
        val response = api.createRepo(token, request)
        if (response.isSuccessful && response.body() != null) {
            _createRepoLiveData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _createRepoLiveData.postValue(Resource.Error(errorObj.getString("message")))
        } else {
            _createRepoLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }


    suspend fun searchUsername(query: String?) {
        _searchUsernameInfoLiveData.postValue(Resource.Loading())
        val response = query?.let { api.searchUsername(it) }
        if (response!!.isSuccessful && response.body() != null) {
            _searchUsernameInfoLiveData.postValue(Resource.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _searchUsernameInfoLiveData.postValue(Resource.Error(errorObj.getString("message")))
        } else {
            _searchUsernameInfoLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }

}