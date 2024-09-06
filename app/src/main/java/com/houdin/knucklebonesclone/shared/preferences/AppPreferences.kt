package com.houdin.knucklebonesclone.shared.preferences

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "KnucklebonesApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val DEVICE_ID = Pair("device_id", "")
    private val PACKAGE_NAME = Pair("package_name", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var deviceId: String
        get() = preferences.getString(DEVICE_ID.first, DEVICE_ID.second).orEmpty()

        set(value) = preferences.edit {
            it.putString(DEVICE_ID.first, value)
        }

    var packageName: String
        get() = preferences.getString(PACKAGE_NAME.first, PACKAGE_NAME.second).orEmpty()

        set(value) = preferences.edit {
            it.putString(PACKAGE_NAME.first, value)
        }
}