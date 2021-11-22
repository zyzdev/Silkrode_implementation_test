package com.example.silkrode_implementation_test.ui.user

import com.example.silkrode_implementation_test.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("users?")
    suspend fun getUsersData(@Query("since") since: Int): ArrayList<User>

    companion object {

        private const val BASE_URL = "https://api.github.com/"
        operator fun invoke(): UserApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}