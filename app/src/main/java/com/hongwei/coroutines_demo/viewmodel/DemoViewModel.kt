package com.hongwei.coroutines_demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.coroutines_demo.model.demo.*
import com.hongwei.coroutines_demo.model.service.DemoService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hongwei.coroutines_demo.model.demo.RxJavaBranch.OnLoadFinishListener as OnLoadFinishListener1

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val coroutineExceptionHelper: CoroutineExceptionHelper,
    private val coroutinesBasicCalls: CoroutinesBasicCalls,
    private val coroutinesCascadeCalls: CoroutinesCascadeCalls,
    private val coroutinesParallelCalls: CoroutinesParallelCalls,
    private val coroutinesParallelCalls2BadExample: CoroutinesParallelCalls2_BadExample,
    private val coroutinesParallelCalls3: CoroutinesParallelCalls3,
    private val coroutinesParallelCalls4: CoroutinesBranch,
    private val rxJavaBranch: RxJavaBranch,
    private val demoService: DemoService
) : ViewModel() {
    fun load(accountNumberString: String) {
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
//            val td = measureTimeMillis {
//                ratesInfo.value = coroutinesParallelCalls4.load(coroutineContext, accountNumberString.toLong())
//            }
//            perf.value = "Api call consumed $td ms"

            // branch
            rxJavaBranch.listener = object : OnLoadFinishListener1 {
                override fun onSuccess(result: String) {
                    loading.value = false
                    ratesInfo.value = result
                }

                override fun onError(exception: Throwable) {
                    loading.value = false
                    error.value = exception
                }
            }
            rxJavaBranch.load(accountNumberString.toLong())

//            loading.value = false
        }
    }

    fun loadAccounts() {
        viewModelScope.launch(coroutineExceptionHelper.handler) {
            loading.value = true
            accounts.value = demoService.getAccounts().accounts.map { it.toString() }
            loading.value = false
        }
    }

    fun cancel() {
        viewModelScope.cancel("Manual cancelling all jobs")
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

    val accounts: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
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