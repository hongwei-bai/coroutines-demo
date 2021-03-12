package com.hongwei.coroutines_demo.model.service

import com.hongwei.coroutines_demo.model.response.AccountsResponse
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RxJavaDemoService {
    @GET("content.do")
    fun getContent(): Observable<ContentResponse>

    @GET("accounts.do")
    fun getAccounts(): Observable<AccountsResponse>

    @GET("rate.do")
    fun getRate(@Query(value = "accountNumber") accountNumber: Long): Observable<RateResponse>
}