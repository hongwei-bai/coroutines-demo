package com.hongwei.coroutines_demo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.coroutines_demo.model.network.NetworkClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.NullPointerException

class DemoViewModel : ViewModel() {
    val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    in 400 until 500 -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Client Error: $exception")
                    in 500 until 600 -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Server Error: $exception")
                    else -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Other Error: $exception")
                }
            }
            is NullPointerException -> Log.w("bbbb", "[CoroutineExceptionHandler]NullPointerException: $exception")
            else -> Log.w("bbbb", "[CoroutineExceptionHandler]Generic exception: $exception")
        }
    }

    init {
        viewModelScope.launch(handler) {
            fetchContentBody()

//            fetchContent()
        }
    }

    private suspend fun fetchContent() {
        val response = NetworkClient.demoApi.getContent()
        Log.d("bbbb", "response: $response")

        Log.d("bbbb", "isSuccessful: ${response.isSuccessful}")

        Log.d("bbbb", "${response.code()}")
    }

    private suspend fun fetchContentBody() {
        val content = NetworkClient.demoApi.getContentBody()
        Log.d("bbbb", "content: ${content.content}")
    }

    private fun produceNullPointerException() {
        var problem: String? = "20"
        problem = null
        val never = problem!!.toLong()
        Log.d("bbbb", "problem: $problem, never: $never")
    }
}