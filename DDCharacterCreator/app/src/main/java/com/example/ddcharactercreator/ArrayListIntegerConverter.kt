package com.example.ddcharactercreator

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object ArrayListIntegerConverter {
    @JvmStatic
    @TypeConverter
    fun fromInteger(value: String?): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @JvmStatic
    @TypeConverter
    fun fromArrayList(list: ArrayList<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}