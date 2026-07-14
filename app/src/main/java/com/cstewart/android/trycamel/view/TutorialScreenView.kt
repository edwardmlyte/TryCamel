package com.cstewart.android.trycamel.view

import android.content.Context
import android.text.util.Linkify
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.cstewart.android.trycamel.databinding.ViewTutorialScreenBinding
import com.cstewart.android.trycamel.tutorial.Screen

class TutorialScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding =
        ViewTutorialScreenBinding.inflate(LayoutInflater.from(context), this)

    var screen: Screen? = null
        set(value) {
            field = value
            updateUI()
        }

    private fun updateUI() {
        val screen = screen ?: return

        binding.image.setImageResource(screen.imageResId)
        binding.title.setText(screen.textResId)
        binding.subtitle.setText(screen.subtitleResId)

        Linkify.addLinks(binding.title, Linkify.WEB_URLS)
    }
}
