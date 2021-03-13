package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.service.RxJavaDemoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaFilter @Inject constructor(
    private val rxJavaDemoService: RxJavaDemoService
) {
    fun load() {
        rxJavaDemoService.getAccounts()
            .filter {
                it.accounts.size > 2
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // No-Op
            }, {
                Log.d("bbbb", "[RxJava]onError: $it")
            })
    }

    private fun handleResponse(content: String) {
        println(content)
    }
}