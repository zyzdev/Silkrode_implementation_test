package com.example.silkrode_implementation_test.ui.userinfo

import com.example.silkrode_implementation_test.model.UserDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfoApi {

    @GET("users/{name}")
    suspend fun getUserInfo(@Path("name") name: String): UserDetail

    companion object {

        private const val BASE_URL = "https://api.github.com/"

        operator fun invoke(): UserInfoApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserInfoApi::class.java)
    }
}