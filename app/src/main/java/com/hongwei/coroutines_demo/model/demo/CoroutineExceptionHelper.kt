package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CoroutineExceptionHelper @Inject constructor() {
    private var postHandler: ((CoroutineContext, Throwable) -> Unit)? = null

    val handler = CoroutineExceptionHandler { coroutineContext, exception ->
        postHandler?.invoke(coroutineContext, exception)
        Log.d("bbbb", "coroutineContext: $coroutineContext")
        Log.d("bbbb", "exception: $exception")
        Log.d("bbbb", "exception.localizedMessage: ${exception.localizedMessage}")
        Log.d("bbbb", "exception.suppressed: ${exception.suppressed}")
        Log.d("bbbb", "exception.cause: ${exception.cause}")
        Log.d("bbbb", "exception.message: ${exception.message}")
        Log.d("bbbb", "exception.stackTrace: ${exception.stackTrace.size}")
        exception.stackTrace.forEachIndexed { i, trace ->
            Log.d("bbbb", "exception.stackTrace[$i]: $trace")
        }

        when (exception) {
            is HttpException -> {
                Log.d("bbbb", "HttpException.code: ${exception.code()}")
                Log.d("bbbb", "HttpException.message: ${exception.message()}")
                Log.d("bbbb", "HttpException.response: ${exception.response()}")

                Log.d("bbbb", "HttpException.response.errorBody: ${exception.response()?.errorBody()}")
                Log.d("bbbb", "HttpException.response.body: ${exception.response()?.body()}")
                Log.d("bbbb", "HttpException.response.headers: ${exception.response()?.headers()}")
                Log.d("bbbb", "HttpException.response.raw: ${exception.response()?.raw()}")
                Log.d("bbbb", "HttpException.response.raw.request.url: ${exception.response()?.raw()?.request?.url}")
                Log.d("bbbb", "HttpException.response.raw.request.body: ${exception.response()?.raw()?.request?.body}")

                when (exception.code()) {
                    in 400 until 500 -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Client Error: $exception")
                    in 500 until 600 -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Server Error: $exception")
                    else -> Log.w("bbbb", "[CoroutineExceptionHandler]HttpException/Other Error: $exception")
                }
            }
            else -> {
                Log.w("bbbb", "[CoroutineExceptionHandler]Non-Http exception: $exception")
            }
        }
    }

    fun onException(postHandler: (CoroutineContext, Throwable) -> Unit) {
        this.postHandler = postHandler
    }
}