package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.response.RateResponse
import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutinesParallelCalls @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(coroutineContext: CoroutineContext, action: (String) -> Unit) {
        withContext(coroutineContext) {
            val content = async { demoService.getContent() }
            val rate = async {
                val accounts = demoService.getAccounts()
                demoService.getRate(accounts.accounts.first())
            }
            action.invoke(RateUtil.toDisplay(content.await().content, rate.await().rate))
            Log.d("bbbb", "CoroutinesParallelCalls finished.")

//            val td = measureTimeMillis {
//
//            }
//            Log.d("bbbb", "consumed : $td ms ")
        }
    }
}