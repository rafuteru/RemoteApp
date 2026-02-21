package my.sdl.smarthome.remoteapp.data.db.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    val code: Int,        // device code from server
    val switchId: String,
    val channel: Int      // channel index from server
)