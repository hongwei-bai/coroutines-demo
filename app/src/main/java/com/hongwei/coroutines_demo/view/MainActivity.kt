package com.hongwei.coroutines_demo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hongwei.coroutines_demo.R
import com.hongwei.coroutines_demo.viewmodel.DemoViewModel
import com.hongwei.coroutines_demo.viewmodel.DemoViewModelCascade
import com.hongwei.coroutines_demo.viewmodel.DemoViewModelCascadeRx
import com.hongwei.coroutines_demo.viewmodel.DemoViewModelRx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //    private val viewModel = DemoViewModel()
    private val viewModelRx = DemoViewModelRx()
//    private val viewModelCascade = DemoViewModelCascade()
    private val viewModelCascadeRx = DemoViewModelCascadeRx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModelRx.start()
        viewModelCascadeRx.start()
    }
}