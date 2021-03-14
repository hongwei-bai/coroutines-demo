package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.response.AccountHolderResponse
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import com.hongwei.coroutines_demo.model.service.RxJavaDemoService
import com.hongwei.coroutines_demo.util.RateUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxJavaBranch @Inject constructor(
    private val rxJavaDemoService: RxJavaDemoService
) {
    var listener: OnLoadFinishListener? = null

    fun load2(accountNumber: Long) {
        Observable.zip(
            rxJavaDemoService.isSaverAccount(accountNumber).flatMap {
                if (it.isSaverAccount) {
                    rxJavaDemoService.getRate(accountNumber)
                } else {
                    null
                }
            },
            rxJavaDemoService.getAccountHolder(accountNumber),
            rxJavaDemoService.getContent(),
            Function3 { rate: RateResponse?, holder: AccountHolderResponse, content: ContentResponse ->
                RateUtil.toDisplay(holder.name, content.content, rate?.rate ?: 0.0)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result: String ->
                    handleResponse(result)
                }, { error: Throwable ->
                    handleError(error)
                }
            )
    }

    fun load(accountNumber: Long) {
        Log.d("bbbb", "[RxJava]Begin")
        rxJavaDemoService.isSaverAccount(accountNumber)
            .flatMap {
                if (it.isSaverAccount) {
                    rxJavaDemoService.getRate(accountNumber)
                } else {
                    null
                }
            }.zipWith(rxJavaDemoService.getContent(), BiFunction { rate: RateResponse, content: ContentResponse ->
                RateUtil.toDisplay(content.content, rate.rate)
            }).zipWith(rxJavaDemoService.getAccountHolder(accountNumber), BiFunction { copy: String, holderRsp: AccountHolderResponse ->
                "Hi ${holderRsp.name},\n$copy"
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rateInfo ->
                handleResponse(rateInfo)
            }, {
                handleError(it)
            })
    }

    private fun handleResponse(rateInfo: String) {
        Log.d("bbbb", "[RxJava]onNext: $rateInfo")
        listener?.onSuccess(rateInfo)
    }

    private fun handleError(error: Throwable) {
        Log.d("bbbb", "[RxJava]onError: ${error.localizedMessage}")
        listener?.onError(error)
    }

    interface OnLoadFinishListener {
        fun onSuccess(rateInfo: String)

        fun onError(error: Throwable)
    }
}