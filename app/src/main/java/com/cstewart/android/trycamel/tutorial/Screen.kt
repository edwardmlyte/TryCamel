package com.cstewart.android.trycamel.tutorial

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class Screen(
    @param:ColorRes private val colorResId: Int,
    @field:StringRes val textResId: Int,
    @field:StringRes val subtitleResId: Int,
    @field:DrawableRes val imageResId: Int,
) {
    fun getColor(context: Context): Int = ContextCompat.getColor(context, colorResId)
}
