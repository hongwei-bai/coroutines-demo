package com.hongwei.coroutines_demo.model.demo

import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutinesParallelCalls3 @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(c: CoroutineContext): String {
        return withContext(c) {
            val content = async { demoService.getContent().content }
            val rate = async {
                val accounts = demoService.getAccounts().accounts
                demoService.getRate(accounts.first()).rate
            }
            RateUtil.toDisplay(content.await(), rate.await())
        }
    }
}