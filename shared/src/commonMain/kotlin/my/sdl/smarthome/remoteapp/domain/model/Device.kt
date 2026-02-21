package my.sdl.smarthome.remoteapp.domain.model

import my.sdl.smarthome.remoteapp.constants.SupportDeviceType

data class Device(
    val id: Long,
    val roomId: Long,
    val name: String,
    val type: SupportDeviceType,
    val switchId: String,
    val channelCount: Int,
    val qos: Int,
    val isRetain: Boolean
)

//fun Device.toDomain(): DeviceModel {
//    return DeviceModel(
//        id = id,
//        roomId = room_id,
//        name = name,
//        type = DeviceType.fromCode(device_code),
//        switchId = switch_id,
//        channelCount = channel_count.toInt(),
//        qos = qos.toInt(),
//        isRetain = is_retain == 1L
//    )
//}