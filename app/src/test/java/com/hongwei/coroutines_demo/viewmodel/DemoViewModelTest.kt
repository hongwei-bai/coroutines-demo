package com.hongwei.coroutines_demo.viewmodel

import com.hongwei.coroutines_demo.model.demo.CoroutineExceptionHelper
import com.hongwei.coroutines_demo.model.demo.CoroutinesBranch
import com.hongwei.coroutines_demo.model.service.DemoService
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
class DemoViewModelTest {
    lateinit var viewModel: DemoViewModel

    @Before
    fun setUp() {
        val coroutinesBranch = mockk<CoroutinesBranch>()
//        val coroutineExceptionHelper = mockk<CoroutineExceptionHelper>()
//        val demoService = mockk<DemoService>()
//        viewModel = DemoViewModel(coroutineExceptionHelper, coroutinesBranch, demoService)
    }

    @Test
    fun test() = runBlocking {
//        val costTimeMillis = measureTimeMillis {
//            viewModel.loadAccounts()
//        }
//        print("costTimeMillis: $costTimeMillis")
    }
}