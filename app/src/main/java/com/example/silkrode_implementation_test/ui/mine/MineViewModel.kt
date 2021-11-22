package com.example.silkrode_implementation_test.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silkrode_implementation_test.model.UserDetail
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mineViewModel = module {
    viewModel { (apiHandler:MineApi) -> MineViewModel(apiHandler) }
}

class MineViewModel(private val apiHandler:MineApi) : ViewModel() {
    private val _data by lazy {
        MediatorLiveData<UserDetail>().apply {
            viewModelScope.launch {
                postValue(apiHandler.getMineInfo())
            }
        }
    }

    val data : LiveData<UserDetail>
        get() = _data
}