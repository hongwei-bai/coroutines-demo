package com.hongwei.coroutines_demo.model.service

import com.hongwei.coroutines_demo.model.response.AccountHolderResponse
import com.hongwei.coroutines_demo.model.response.AccountsResponse
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DemoService {
    @GET("content.do")
    suspend fun getContentWithWrapper(): Response<*>

    @GET("content-slow.do")
    suspend fun getContent(): ContentResponse

    @GET("accounts-slow.do")
    suspend fun getAccounts(): AccountsResponse

    @GET("rate-slow.do")
    suspend fun getRate(@Query(value = "accountNumber") accountNumber: Long): RateResponse

    @GET("accountholder-slow.do")
    suspend fun getAccountHolder(@Query(value = "accountNumber") accountNumber: Long): AccountHolderResponse
}