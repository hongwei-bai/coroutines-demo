package com.hongwei.coroutines_demo.model.demo

import android.annotation.SuppressLint
import android.util.Log
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import com.hongwei.coroutines_demo.model.service.RxJavaDemoService
import com.hongwei.coroutines_demo.util.RateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaParallelCalls @Inject constructor(
    private val rxJavaDemoService: RxJavaDemoService
) {
    @SuppressLint("CheckResult")
    fun load() {
        rxJavaDemoService.getContent().zipWith(rxJavaDemoService.getRate(1234567890L),
            BiFunction { content: ContentResponse, rate: RateResponse -> Pair(content, rate) })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleResponse(it)
            }, {
                handleError(it)
            })
    }

    private fun handleError(throwable: Throwable) {
        Log.d("bbbb", "[RxJava]onError: $throwable")
    }

    private fun handleResponse(data: Pair<ContentResponse, RateResponse>) {
        Log.d("bbbb", "display: ${RateUtil.toDisplay(data.first.content, data.second.rate)}")
    }
}