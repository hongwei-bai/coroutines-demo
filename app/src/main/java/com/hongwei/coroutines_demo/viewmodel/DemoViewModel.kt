package com.hongwei.coroutines_demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.coroutines_demo.model.demo.*
import com.hongwei.coroutines_demo.model.service.DemoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val coroutineExceptionHelper: CoroutineExceptionHelper,
    private val coroutinesBasicCalls: CoroutinesBasicCalls,
    private val coroutinesCascadeCalls: CoroutinesCascadeCalls,
    private val coroutinesParallelCalls: CoroutinesParallelCalls,
    private val coroutinesParallelCalls2BadExample: CoroutinesParallelCalls2_BadExample,
    private val coroutinesParallelCalls3: CoroutinesParallelCalls3,
    private val coroutinesParallelCalls4: CoroutinesParallelCalls4,
    private val demoService: DemoService
) : ViewModel() {
    fun load() {
        viewModelScope.launch(coroutineExceptionHelper.handler) {
            loading.value = true

//            coroutinesBasicCalls.fetchContentWithWrapper()

            // (Plan 1)
//            ratesInfo.value = coroutinesCascadeCalls.load()

            // (Plan 2)
//            coroutinesParallelCalls.load(coroutineContext) {
//                ratesInfo.value = it
//                loading.value = false
//            }

            // (Plan 3)
//            val content = async { demoService.getContent() }
//            val rate = async {
//                val accounts = demoService.getAccounts()
//                demoService.getRate(accounts.accounts.first())
//            }
//            ratesInfo.value = RateUtil.toDisplay(content.await().content, rate.await().rate)

            // (Plan 4)
//            val td = measureTimeMillis {
//                ratesInfo.value = coroutinesParallelCalls2BadExample.load(coroutineContext)
//            }
//            perf.value = "Api call consumed $td ms"

            // (Plan 5)
//            val td = measureTimeMillis {
//                ratesInfo.value = coroutinesParallelCalls3.load(coroutineContext)
//            }
//            perf.value = "Api call consumed $td ms"

            // (Plan 6)
            val td = measureTimeMillis {
                ratesInfo.value = coroutinesParallelCalls4.load(coroutineContext)
            }
            perf.value = "Api call consumed $td ms"

            loading.value = false
        }
    }

    init {
        coroutineExceptionHelper.onException { _, throwable ->
            loading.value = false
            error.value = throwable
        }
    }

    val ratesInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val perf: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val error: MutableLiveData<Throwable?> by lazy {
        MutableLiveData<Throwable?>()
    }
}