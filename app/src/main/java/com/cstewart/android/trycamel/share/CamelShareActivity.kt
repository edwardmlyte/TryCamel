package com.cstewart.android.trycamel.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cstewart.android.trycamel.IntentParser
import com.cstewart.android.trycamel.R
import com.cstewart.android.trycamel.TryCamelPreferences

class CamelShareActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = TryCamelPreferences(this)
        val camelUri = IntentParser.parseIntent(preferences.url, intent)
        if (camelUri == null) {
            Toast.makeText(this, R.string.parse_error, Toast.LENGTH_LONG).show()
            Log.e(TAG, "Unable to send to CamelCamelCamel: $intent")
        } else {
            val shareIntent = Intent(Intent.ACTION_VIEW).apply { data = camelUri }
            startActivity(shareIntent)
        }

        finish()
    }

    companion object {
        private const val TAG = "CamelShareActivity"
    }
}
