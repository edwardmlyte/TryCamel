package com.cstewart.android.trycamel

import android.content.Intent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Runs on the JVM via Robolectric so CI can verify [IntentParser] without a
 * device/emulator. Robolectric supplies real implementations of Android's
 * [android.net.Uri], [android.util.Patterns], and [android.text.TextUtils].
 */
@RunWith(RobolectricTestRunner::class)
class IntentParserTest {

    private val defaultUrl = "http://camelcamelcamel.com/search?q=%1\$s"

    private val validUrl =
        "http://www.amazon.com/Android-Programming-Nerd-Ranch-Guide/dp/0134171454/ref=sr_1_3?ie=UTF8&qid=1438264449&sr=8-3&keywords=android+programming"
    private val validExpected =
        "http://camelcamelcamel.com/search?q=http%3A%2F%2Fwww.amazon.com%2FAndroid-Programming-Nerd-Ranch-Guide%2Fdp%2F0134171454%2Fref%3Dsr_1_3%3Fie%3DUTF8%26qid%3D1438264449%26sr%3D8-3%26keywords%3Dandroid%2Bprogramming"

    @Test
    fun validSendExtraText() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, validUrl)
            putExtra(Intent.EXTRA_SUBJECT, "Testing")
        }

        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertEquals(validExpected, uri.toString())
    }

    @Test
    fun validSendSubjectText() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, "Testing")
            putExtra(Intent.EXTRA_SUBJECT, validUrl)
        }

        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertEquals(validExpected, uri.toString())
    }

    @Test
    fun invalidExtras() {
        val intent = Intent(Intent.ACTION_SEND)
        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertNull(uri)
    }

    @Test
    fun invalidAction() {
        val intent = Intent("Invalid Action").apply {
            putExtra(Intent.EXTRA_SUBJECT, validUrl)
        }

        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertEquals(validExpected, uri.toString())
    }

    @Test
    fun emptyIntent() {
        val uri = IntentParser.parseIntent(defaultUrl, Intent())
        assertNull(uri)
    }

    @Test
    fun complexShareIntent() {
        val shareFormat = "High Bounce Balance Bike Adjustable from 11''-16'' With a Hand Brake(Pink) %1\$s"
        val amazonUrl = "https://www.amazon.com/dp/B00VETQ44W/ref=cm_sw_r_other_awd_inuVwb3EZEFC1"
        val expectedUrl =
            "http://camelcamelcamel.com/search?q=https%3A%2F%2Fwww.amazon.com%2Fdp%2FB00VETQ44W%2Fref%3Dcm_sw_r_other_awd_inuVwb3EZEFC1"
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, String.format(shareFormat, amazonUrl))
        }

        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertEquals(expectedUrl, uri.toString())
    }

    @Test
    fun textFallback() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, "AmazonID")
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
        }

        val expectedUrl = "http://camelcamelcamel.com/search?q=AmazonID"
        val uri = IntentParser.parseIntent(defaultUrl, intent)
        assertEquals(expectedUrl, uri.toString())
    }
}
