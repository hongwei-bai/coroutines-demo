package com.hongwei.coroutines_demo.model.network

import com.hongwei.coroutines_demo.constants.ApiEndpoints
import com.hongwei.coroutines_demo.model.service.ALeagueService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkClient {
    val api = Retrofit.Builder()
        .baseUrl(ApiEndpoints.EndPoint)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
        .create(ALeagueService::class.java)
}