package com.hongwei.coroutines_demo.model.service

import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface DemoService {
    @GET("content.do")
    suspend fun getContent(): Response<*>

    @GET("content-slow.do")
    suspend fun getContentBody(): ContentResponse

    @GET("content-slow.do")
    fun getContentRx(): Observable<ContentResponse>

    // rate
    @GET("rate-slow.do")
    suspend fun getRate(): RateResponse

    @GET("rate-slow.do")
    fun getRateRx(): Observable<RateResponse>
}