package com.hongwei.coroutines_demo.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.hongwei.coroutines_demo.base.view.BaseActivity
import com.hongwei.coroutines_demo.databinding.ActivityMainBinding
import com.hongwei.coroutines_demo.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: DemoViewModel by viewModels()

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        observe()

        setupClickActions()
    }

    private fun observe() {
        viewModel.loading.observe(this) { loading ->
            viewBinding.loadingSpinner.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.ratesInfo.observe(this) { ratesInfo ->
            viewBinding.textRates.text = ratesInfo
        }

        viewModel.perf.observe(this) { perf ->
            viewBinding.textPerf.text = perf
        }

        viewModel.error.observe(this) { exception ->
            viewBinding.loadingSpinner.visibility = View.GONE
            exception?.let {
                Snackbar.make(viewBinding.root, "ERROR: ${exception.localizedMessage}", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickActions() {
        viewBinding.buttonLoad.setOnClickListener {
            clear()
            viewModel.load()
        }
        viewBinding.buttonClear.setOnClickListener { clear() }
    }

    private fun clear() {
        viewBinding.textRates.text = ""
        viewBinding.textPerf.text = ""
    }
}