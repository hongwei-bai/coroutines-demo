package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.service.RxJavaDemoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaBasicCalls @Inject constructor(
    private val rxJavaDemoService: RxJavaDemoService
) {
    fun start() {
        rxJavaDemoService.getContent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("bbbb", "[RxJava]onNext: $it")
                handleResponse(it)
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