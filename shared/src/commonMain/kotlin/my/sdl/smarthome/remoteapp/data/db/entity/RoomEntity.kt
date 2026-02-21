package my.sdl.smarthome.remoteapp.data.db.entity

import my.sdl.smarthome.remoteapp.domain.model.Room


typealias RoomEntity = mysdlsmarthomeremoteapp.Room


// Entity → Domain
fun RoomEntity.toDomain(): Room {
    return Room(
        id = id,
        name = name,
        roomKey = room_key,
    )
}

// Domain → Entity
fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        name = name,
        room_key = roomKey,
    )
}