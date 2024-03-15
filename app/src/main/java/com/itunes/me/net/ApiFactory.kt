package com.itunes.me.net

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val timeoutTime = 10000L

    private val client = OkHttpClient().newBuilder()
        .writeTimeout(timeoutTime, TimeUnit.MILLISECONDS)
        .connectTimeout(timeoutTime, TimeUnit.MILLISECONDS)
        .connectTimeout(timeoutTime, TimeUnit.MILLISECONDS)
        .addInterceptor(
            LoggingInterceptor.Builder().setLevel(Level.BASIC).log(Platform.WARN).tag("itunes")
                .build()
        )
        .build()

    private fun initRetrofit() = Retrofit.Builder()
        .client(client)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
        .build()

    val apiService by lazy { initRetrofit().create(ApiService::class.java) }

}