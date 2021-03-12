package com.hongwei.coroutines_demo.model.demo

import com.hongwei.coroutines_demo.model.service.DemoService
import com.hongwei.coroutines_demo.util.RateUtil
import javax.inject.Inject

class CoroutinesCascadeCalls @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun load(): String {
        val content = demoService.getContent()

        val accounts = demoService.getAccounts()

        val rate = demoService.getRate(accounts.accounts.first())

        return RateUtil.toDisplay(content.content, rate.rate)
    }
}