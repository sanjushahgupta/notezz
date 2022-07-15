package com.example.noteappviaapi

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val preference_Name ="Login vaierahosa"
val sharedPref: SharedPreferences = context.getSharedPreferences(preference_Name, Context.MODE_PRIVATE)

    fun save_String(key_name: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key_name, text)
        editor.commit()
    }

    fun getPreferenceString(key_name: String): String? {
        return sharedPref .getString(key_name, null )
    }
    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref .edit()
        editor.clear()
        editor.commit()
    }
}
