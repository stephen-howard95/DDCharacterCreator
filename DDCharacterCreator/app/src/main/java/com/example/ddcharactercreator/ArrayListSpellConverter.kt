package com.example.ddcharactercreator

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object ArrayListSpellConverter {
    @JvmStatic
    @TypeConverter
    fun fromSpell(value: String?): ArrayList<Spell> {
        val listType = object : TypeToken<ArrayList<Spell?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromArrayList(list: ArrayList<Spell?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}