package com.hongwei.coroutines_demo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hongwei.coroutines_demo.model.network.NetworkClient
import com.hongwei.coroutines_demo.model.response.ContentResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DemoViewModelRx : ViewModel() {
    fun start() {
        NetworkClient.demoApi.getContentRx()
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