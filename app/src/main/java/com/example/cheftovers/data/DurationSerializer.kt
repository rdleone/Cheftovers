package com.example.cheftovers.data

import android.util.Log
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Duration

/**
 * Custom serializer for java.time.Duration, since the current
 * Serialization version apparently does not have its own.
 * Serializes Duration to String
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Duration::class)
object DurationSerializer : KSerializer<Duration> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("jsonDuration", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Duration {
        // Imagine having invalid Duration strings in your JSON file
        // that need to be accounted for. Couldn't be me
        val str = decoder.decodeString()
        val res = try {
            Duration.parse(str)
        } catch(e: Exception) {
            Duration.ZERO
        }
        return res
    }
}