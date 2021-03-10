package com.hongwei.coroutines_demo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hongwei.coroutines_demo.model.network.NetworkClient
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.util.RateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DemoViewModelCascadeRx : ViewModel() {
    fun start() {
        val t0 = System.currentTimeMillis()
        NetworkClient.demoApi.getContentRx()
            .flatMap { content ->
                NetworkClient.demoApi.getRateRx().map { rate ->
                    Pair(content, rate)
                }
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val td = System.currentTimeMillis() - t0
                Log.d("bbbb", "display: ${RateUtil.toDisplay(it.first.content, it.second.rate)}, consumed: $td ms")
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