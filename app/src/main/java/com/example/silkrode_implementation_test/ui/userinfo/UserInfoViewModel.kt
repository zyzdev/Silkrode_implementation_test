package com.example.silkrode_implementation_test.ui.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silkrode_implementation_test.model.UserDetail
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userInfoViewModel = module {
    viewModel { (apiHandler:UserInfoApi, name: String) -> UserInfoViewModel(apiHandler, name) }
}

class UserInfoViewModel(private val apiHandler:UserInfoApi, private val name: String) : ViewModel() {
    private val _data by lazy {
        MediatorLiveData<UserDetail>().apply {
            viewModelScope.launch {
                postValue(apiHandler.getUserInfo(name))
            }
        }
    }

    val data : LiveData<UserDetail>
        get() = _data
}