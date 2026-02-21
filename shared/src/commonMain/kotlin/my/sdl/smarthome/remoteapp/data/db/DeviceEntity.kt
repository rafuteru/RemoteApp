package my.sdl.smarthome.remoteapp.data.db

import my.sdl.smarthome.remoteapp.constants.SupportDeviceType
import my.sdl.smarthome.remoteapp.domain.model.Device

typealias DeviceEntity = mysdlsmarthomeremoteapp.Device

// Entity → Domain
fun DeviceEntity.toDomain(): Device {
    return Device(
        id = id,
        roomId = room_id,
        name = name,
        type = SupportDeviceType.fromCode(device_code.toInt())!!,
        switchId = switch_id,
        channelCount = channel_count.toInt(),
        qos = qos.toInt(),
        isRetain = is_retain == 1L
    )
}

// Domain → Entity
fun Device.toEntity(): DeviceEntity {
    return DeviceEntity(
        id = id,
        room_id = roomId,
        name = name,
        device_code = type.code.toLong(),
        switch_id = switchId,
        channel_count = channelCount.toLong(),
        qos = qos.toLong(),
        is_retain = if (isRetain) 1L else 0L
    )
}