package com.subha.myusergithubapp.presentation.landing_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.data.models.SearchUserRes
import com.subha.myusergithubapp.data.models.UserRepoResItem
import com.subha.myusergithubapp.data.models.UserRes
import com.subha.myusergithubapp.repository.GithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(private val repository: GithubRepo) : ViewModel() {

    val userInfoLiveData: LiveData<Resource<UserRes>>
        get() = repository.userInfoLiveData


    val userRepoInfoLiveData: LiveData<Resource<ArrayList<UserRepoResItem>>>
        get() = repository.userRepoInfoLiveData


    val searchuserLiveData: LiveData<Resource<SearchUserRes>>
        get() = repository.searchUsernameInfoLiveData

    fun getUserInfo(url: String) {
        viewModelScope.launch {
            repository.getuserinfo(url)
        }
    }


    fun getUserRepoInfo(url: String) {
        viewModelScope.launch {
            repository.getUserRepoInfo(url)
        }
    }

    fun searchUserName(query: String?) {
        viewModelScope.launch {
            repository.searchUsername(query)
        }
    }


}