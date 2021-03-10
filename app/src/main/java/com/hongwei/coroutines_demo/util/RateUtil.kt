package com.hongwei.coroutines_demo.util

import java.text.DecimalFormat

object RateUtil {
    fun toDisplay(content: String, rate: Double) = content.replace("{rate}", formatRate(rate), false)

    private fun formatRate(rate: Double): String {
        val decimalFormat = DecimalFormat("#,###.##")
        val display = decimalFormat.format(rate)
        return display
    }
}