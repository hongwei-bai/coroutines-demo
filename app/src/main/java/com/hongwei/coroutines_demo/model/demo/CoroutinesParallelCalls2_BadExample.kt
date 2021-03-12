package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.response.ContentResponse
import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutinesParallelCalls2_BadExample @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(c: CoroutineContext): String {
        return withContext(c) {
            RateUtil.toDisplay(fetchContent(c).await(), fetchRate(c).await())
        }
    }

    private suspend fun fetchContent(coroutineContext: CoroutineContext): Deferred<String> {
        return withContext(coroutineContext) {
            async {
                Log.d("bbbb", "getContent begin")
                val r = demoService.getContent().content
                Log.d("bbbb", "getContent end")
                r
            }
        }
    }

    private suspend fun fetchRate(coroutineContext: CoroutineContext): Deferred<Double> {
        return withContext(coroutineContext) {
            async {
                Log.d("bbbb", "getAccounts begin")
                val accounts = demoService.getAccounts().accounts
                Log.d("bbbb", "getRate begin")
                val r = demoService.getRate(accounts.first()).rate
                Log.d("bbbb", "getRate end")
                r
            }
        }
    }
}