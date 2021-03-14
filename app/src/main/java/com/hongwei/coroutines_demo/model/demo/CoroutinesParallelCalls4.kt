package com.hongwei.coroutines_demo.model.demo

import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutinesParallelCalls4 @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(c: CoroutineContext): String {
        return withContext(c) {
            val content = loadContentAsync()
            val rate = loadRateAsync()
            RateUtil.toDisplay(content.await(), rate.await())
        }
    }

//    private fun loadContentAsync(c: CoroutineContext): Deferred<String> = CoroutineScope(c).async {
//        demoService.getContent().content
//    }
//
//    private fun loadRateAsync(c: CoroutineContext): Deferred<Double> = CoroutineScope(c).async {
//        val accounts = demoService.getAccounts().accounts
//        demoService.getRate(accounts.first()).rate
//    }

    private fun CoroutineScope.loadContentAsync(): Deferred<String> = async {
        demoService.getContent().content
    }

    private fun CoroutineScope.loadRateAsync(): Deferred<Double> = async {
        val accounts = demoService.getAccounts().accounts
        demoService.getRate(accounts.first()).rate
    }
}