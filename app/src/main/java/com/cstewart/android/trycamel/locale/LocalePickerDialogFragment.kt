package com.cstewart.android.trycamel.locale

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cstewart.android.trycamel.R
import com.cstewart.android.trycamel.TryCamelPreferences

class LocalePickerDialogFragment : DialogFragment() {

    private var selectedIndex = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val preferences = TryCamelPreferences(requireContext())
        val savedIndex = getCurrentIndex(preferences.url)
        selectedIndex = savedIndex

        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.chose_amazon_store)
            .setSingleChoiceItems(R.array.locale_name, savedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val localeUrls = resources.getStringArray(R.array.locale_url)
                preferences.url = localeUrls[selectedIndex]
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    private fun getCurrentIndex(currentUrl: String): Int {
        val localeUrls = resources.getStringArray(R.array.locale_url)
        return localeUrls.indexOfFirst { it == currentUrl }.takeIf { it >= 0 } ?: 0
    }
}
