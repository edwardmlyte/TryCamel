package com.cstewart.android.trycamel

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns

/**
 * Extracts an Amazon URL (or fallback text) from a share [Intent] and builds the
 * CamelCamelCamel search URL using [urlFormat] (a `String.format` template with a
 * single `%1$s` placeholder for the URL-encoded query).
 */
object IntentParser {

    @JvmStatic
    fun parseIntent(urlFormat: String, intent: Intent): Uri? {
        val potentialUrls = listOf(
            intent.getStringExtra(Intent.EXTRA_TEXT),
            intent.getStringExtra(Intent.EXTRA_SUBJECT),
            intent.dataString,
        )

        // 1. Hunt for a URL in any of the candidate fields.
        for (candidate in potentialUrls) {
            val foundUrl = findUrl(candidate) ?: continue

            val amazonId = Uri.encode(foundUrl)
            if (TextUtils.isEmpty(amazonId)) {
                return null
            }

            return Uri.parse(String.format(urlFormat, amazonId))
        }

        // 2. Fall back on the raw EXTRA_TEXT value if there is one.
        val extraText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (!TextUtils.isEmpty(extraText)) {
            val amazonId = Uri.encode(extraText)
            if (TextUtils.isEmpty(amazonId)) {
                return null
            }

            return Uri.parse(String.format(urlFormat, amazonId))
        }

        return null
    }

    private fun findUrl(value: String?): String? {
        if (TextUtils.isEmpty(value)) {
            return null
        }

        val matcher = Patterns.WEB_URL.matcher(value!!)
        return if (matcher.find()) matcher.group(0) else null
    }
}
