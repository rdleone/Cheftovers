package com.example.cheftovers.util

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

class Converters {
    @TypeConverter
    fun fromList(value: List<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String?) = Json.decodeFromString<List<String>>(value!!)

    @TypeConverter
    fun fromListElement(value: List<String>) = Json.encodeToJsonElement(value)

    @TypeConverter
    fun toList(value: JsonElement) = Json.decodeFromJsonElement<List<String>>(value)
}