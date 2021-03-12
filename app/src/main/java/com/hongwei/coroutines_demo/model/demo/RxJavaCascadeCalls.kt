package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.service.RxJavaDemoService
import com.hongwei.coroutines_demo.util.RateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaCascadeCalls @Inject constructor(
    private val rxJavaDemoService: RxJavaDemoService
) {
    fun load() {
        val t0 = System.currentTimeMillis()
        rxJavaDemoService.getAccounts()
            .flatMap { accounts ->
                rxJavaDemoService.getRate(accounts.accounts.first()).map { rate ->
                    rate
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val td = System.currentTimeMillis() - t0
                Log.d("bbbb", "display: ${RateUtil.toDisplay("{rate}", it.rate)}, consumed: $td ms")
            }, {
                Log.d("bbbb", "[RxJava]onError: $it")
            }, {
                Log.d("bbbb", "[RxJava]onComplete")
            })
    }

    private fun handleResponse(contentResponse: ContentResponse) {
        val number = contentResponse.content.toLong()
        println(number)
    }
}