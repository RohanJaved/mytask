package com.example.newproject.utils

import android.content.Context

object PrefsHelper {
    fun saveLastSearch(context: Context, type: String, value: String) {
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .edit().putString("type", type).putString("value", value).apply()
    }

    fun getLastSearch(context: Context): Pair<String, String>? {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val type = prefs.getString("type", null)
        val value = prefs.getString("value", null)
        return if (type != null && value != null) type to value else null
    }
}