package my.sdl.smarthome.remoteapp.data.db.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val roomKey: String,
    val name: String,
    val devices: List<DeviceDto>
)