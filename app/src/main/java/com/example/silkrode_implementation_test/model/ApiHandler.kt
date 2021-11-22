package com.example.silkrode_implementation_test.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHandler {

    @GET("users?")
    suspend fun getUsersData(@Query("since") since: Int): ArrayList<User>

    @GET("users/{name}")
    suspend fun getUserInfo(@Path("name") name: String): UserDetail

    companion object {

        private const val BASE_URL = "https://api.github.com/"

        fun destroy() {
            _instance = null
        }

        private var _instance: ApiHandler? = null
        val instance: ApiHandler
            get() {
                if(_instance == null) _instance = ApiHandler()
                return _instance!!
            }

        operator fun invoke(): ApiHandler = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiHandler::class.java)
    }
}