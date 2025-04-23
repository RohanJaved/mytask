package com.example.newproject.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}



inline fun <reified T> Context.saveObject(key: String, obj: T) {
    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
    val gson = Gson()
    val jsonString = gson.toJson(obj)
    sharedPreferences.edit().putString(key, jsonString).apply()
}

inline fun <reified T> Context.getObject(key: String): T? {
    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
    val gson = Gson()
    val jsonString = sharedPreferences.getString(key, null)
    return if (jsonString != null) {
        gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
    } else {
        null
    }
}

fun Context.removeObject(key: String) {
    val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
    sharedPreferences.edit().remove(key).apply()
}