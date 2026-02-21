package my.sdl.smarthome.remoteapp.utils

import kotlinx.serialization.json.Json
import my.sdl.smarthome.remoteapp.model.SensorHubResponse
import kotlin.jvm.JvmStatic

object AppUtils {
    @JvmStatic
    fun parseSensorHubData(value: String?): SensorHubResponse {
        return value?.let {
            Json.decodeFromString<SensorHubResponse>(value)
        } ?: SensorHubResponse()
    }
}