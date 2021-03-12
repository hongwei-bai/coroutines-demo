package com.hongwei.coroutines_demo.model.demo

import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutinesParallelCalls4 @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(coroutineContext: CoroutineContext): String {
        val (holder, content, rate) = coroutineContext.runInParallel(
            { demoService.getAccountHolder(0L).name },
            { demoService.getContent().content },
            {
                val accounts = demoService.getAccounts().accounts
                demoService.getRate(accounts.first()).rate
            }
        )
        return RateUtil.toDisplay(holder as String, content as String, rate as Double)
    }

    suspend fun load2(coroutineContext: CoroutineContext): String {
        val r = coroutineContext.runInParallel(
            { demoService.getContent().content },
            {
                val accounts = demoService.getAccounts().accounts
                demoService.getRate(accounts.first()).rate
            }
        )
        return RateUtil.toDisplay(r[0] as String, r[1] as Double)
    }

    private suspend fun CoroutineContext.runInParallel(vararg functions: suspend () -> Any): Array<*> {
        return withContext(this) {
            val a1 = Array<Deferred<*>?>(functions.size) { null }
            functions.forEachIndexed { i, _ ->
                a1[i] = async {
                    functions[i].invoke()
                }
            }
            val r = Array<Any?>(functions.size) { null }
            a1.forEachIndexed { i, element ->
                r[i] = element?.await()
            }
            r
        }
    }
}