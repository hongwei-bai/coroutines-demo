package com.hongwei.coroutines_demo.model.service

import com.hongwei.coroutines_demo.model.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DemoService {
    @GET("content.do")
    suspend fun getContentWithWrapper(): Response<*>

    @GET("content-slow.do")
    suspend fun getContent(): ContentResponse

    @GET("accounts.do")
    suspend fun getAccounts(): AccountsResponse

    @GET("rate.do")
    suspend fun getRate(@Query(value = "accountNumber") accountNumber: Long): RateResponse

    @GET("accountholder-slow.do")
    suspend fun getAccountHolder(@Query(value = "accountNumber") accountNumber: Long): AccountHolderResponse

    @GET("accounttype-slow.do")
    suspend fun getAccountType(@Query(value = "accountNumber") accountNumber: Long): AccountHolderResponse

    @GET("issaveraccount-slow.do")
    suspend fun isSaverAccount(@Query(value = "accountNumber") accountNumber: Long): IsSaverAccountResponse
}