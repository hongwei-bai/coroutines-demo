package com.hongwei.coroutines_demo.model.demo

import android.util.Log
import com.hongwei.coroutines_demo.model.service.DemoService
import javax.inject.Inject

class CoroutinesBasicCalls @Inject constructor(
    private val demoService: DemoService
) {
    suspend fun fetchContentWithWrapper() {
        val response = demoService.getContentWithWrapper()
        Log.d("bbbb", "response: $response")

        Log.d("bbbb", "isSuccessful: ${response.isSuccessful}")

        Log.d("bbbb", "${response.code()}")

        produceNullPointerException()
    }

    suspend fun fetchContent() {
        val content = demoService.getContent()
        Log.d("bbbb", "content: ${content.content}")
    }

    private fun produceNullPointerException() {
        var problem: String? = "20"
        problem = null
        @Suppress("ALWAYS_NULL") val never = problem!!.toLong()
        Log.d("bbbb", "problem: $problem, never: $never")
    }
}