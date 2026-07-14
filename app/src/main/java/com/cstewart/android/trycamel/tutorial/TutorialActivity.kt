package com.cstewart.android.trycamel.tutorial

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cstewart.android.trycamel.R
import com.cstewart.android.trycamel.databinding.ActivityTutorialBinding
import com.cstewart.android.trycamel.locale.LocalePickerDialogFragment
import com.cstewart.android.trycamel.view.TutorialScreenView
import com.google.android.material.tabs.TabLayoutMediator

class TutorialActivity : AppCompatActivity() {

    private val screens = listOf(
        Screen(R.color.amber_500, R.string.tutorial_intro, R.string.tutorial_intro_subtitle, R.drawable.tutorial_1),
        Screen(R.color.brown_500, R.string.tutorial_intro_2, R.string.tutorial_intro_subtitle_2, R.drawable.tutorial_2),
        Screen(R.color.green_500, R.string.tutorial_share_instruction, R.string.tutorial_share_instruction_subtitle, R.drawable.tutorial_amazon_share),
        Screen(R.color.green_500, R.string.tutorial_screen_share_browser, R.string.tutorial_screen_share_browser_subtitle, R.drawable.tutorial_browser_share),
        Screen(R.color.green_500, R.string.tutorial_screen_share_fake, R.string.tutorial_screen_share_fake_subtitle, R.drawable.tutorial_fake_share),
        Screen(R.color.light_blue_500, R.string.tutorial_screen_4, R.string.tutorial_screen_subtitle_4, R.drawable.tutorial_4),
        Screen(R.color.blue_gray_700, R.string.tutorial_screen_5, R.string.tutorial_screen_subtitle_5, R.drawable.tutorial_1),
    )

    private lateinit var binding: ActivityTutorialBinding
    private val argbEvaluator = ArgbEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateForwardButtonVisibility(isLastPage = false)

        binding.viewpager.adapter = TutorialAdapter(screens)
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)

        TabLayoutMediator(binding.indicator, binding.viewpager) { _, _ -> }.attach()

        binding.next.setOnClickListener {
            binding.viewpager.setCurrentItem(binding.viewpager.currentItem + 1, true)
        }
        binding.done.setOnClickListener { finish() }
        binding.locale.setOnClickListener {
            LocalePickerDialogFragment().show(supportFragmentManager, TAG_LOCALE_DIALOG)
        }
    }

    private fun updateForwardButtonVisibility(isLastPage: Boolean) {
        binding.next.visibility = if (isLastPage) View.GONE else View.VISIBLE
        binding.done.visibility = if (isLastPage) View.VISIBLE else View.GONE
    }

    private fun colorAt(position: Int): Int = screens[position].getColor(this)

    private fun isLastPage(position: Int): Boolean = position >= screens.size - 1

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val currentColor = colorAt(position)
            val calculatedColor = if (isLastPage(position)) {
                currentColor
            } else {
                argbEvaluator.evaluate(positionOffset, currentColor, colorAt(position + 1)) as Int
            }
            binding.root.setBackgroundColor(calculatedColor)
        }

        override fun onPageSelected(position: Int) {
            updateForwardButtonVisibility(isLastPage(position))
        }
    }

    private class TutorialAdapter(
        private val screens: List<Screen>,
    ) : RecyclerView.Adapter<TutorialAdapter.ScreenViewHolder>() {

        class ScreenViewHolder(val view: TutorialScreenView) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenViewHolder {
            val view = TutorialScreenView(parent.context).apply {
                layoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
            return ScreenViewHolder(view)
        }

        override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
            holder.view.screen = screens[position]
        }

        override fun getItemCount(): Int = screens.size
    }

    companion object {
        const val TAG_LOCALE_DIALOG = "locale_dialog"
    }
}
