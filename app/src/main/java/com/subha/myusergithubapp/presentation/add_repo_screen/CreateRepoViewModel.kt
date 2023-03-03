package com.subha.myusergithubapp.presentation.add_repo_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.data.models.CreateRepoRequest
import com.subha.myusergithubapp.data.models.CreateRepoRes
import com.subha.myusergithubapp.repository.GithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateRepoViewModel @Inject constructor(private val repository: GithubRepo) : ViewModel() {

    val createRepoLiveData: LiveData<Resource<CreateRepoRes>>
        get() = repository.createRepoLiveData


    fun createRepository(token: String, request: CreateRepoRequest) {
        viewModelScope.launch {
            repository.createRepo(token, request)
        }
    }

}