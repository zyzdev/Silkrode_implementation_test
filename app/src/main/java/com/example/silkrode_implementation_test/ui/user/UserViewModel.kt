package com.example.silkrode_implementation_test.ui.user

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import org.koin.dsl.module

val userViewModel = module {
    single { UserViewModel(UserApi()) }
}

class UserViewModel(private val apiHandler: UserApi) : ViewModel() {

    val users = Pager(PagingConfig(pageSize = 5,
        enablePlaceholders = false), pagingSourceFactory = {
        UsersDataSource(apiHandler)
    }).flow.cachedIn(viewModelScope)

}
