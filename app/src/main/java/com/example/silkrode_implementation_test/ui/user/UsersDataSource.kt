package com.example.silkrode_implementation_test.ui.user

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.silkrode_implementation_test.model.User
import com.example.silkrode_implementation_test.util.LogUtil

class UsersDataSource(
    private val apiHandler: UserApi
) : PagingSource<Int, User>() {

    private val dTag by lazy { UserViewModel::class.java.simpleName }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val key = params.key
            val data = apiHandler.getUsersData(key ?: 0)
            val nextKey = data.last().id
            LogUtil.d(dTag, "nextKey:$nextKey")
            LoadResult.Page(
                data = data,
                prevKey = key,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}