package com.itunes.me.util

import android.content.Context
import android.util.TypedValue

fun Context.dp(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, value,
        resources.displayMetrics
    ).toInt()
}