package com.example.cheftovers.util

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import java.time.Duration

/**
 * Contains custom TypeConverters that allow storage of incompatible
 * data types to a Room database through the Serialization library.
 */
class Converters {

    @TypeConverter
    fun fromList(value: List<String>?) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String?) = Json.decodeFromString<List<String>>(value!!)

    @TypeConverter
    fun fromJsonString(value: String): JsonArray {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun toJsonString(value: JsonArray): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun fromDuration(duration: Duration): Long {
        return duration.toMinutes()
    }

    @TypeConverter
    fun toDuration(value: Long): Duration {
        return Duration.ofMinutes(value)
    }
}