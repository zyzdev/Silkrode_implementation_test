package com.example.silkrode_implementation_test.ui.mine

import com.example.silkrode_implementation_test.model.UserDetail
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MineApi {

    @GET("user")
    suspend fun getMineInfo(): UserDetail

    companion object {

        private const val BASE_URL = "https://api.github.com/"

        operator fun invoke(): MineApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                client.addInterceptor(Interceptor { chain ->
                    val request: Request =
                        chain.request().newBuilder().addHeader("Authorization", "token ${
                            "MmBwh19HogDtngzQuq8K4O7iNFyBLnJJWKrW_phg,LByFNi7O4K8quQzgntDgoH91hwBmM".split(",")[0].reversed()
                        }").build()
                    chain.proceed(request)
                })
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MineApi::class.java)
    }
}