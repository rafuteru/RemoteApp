package my.sdl.smarthome.remoteapp.domain.repository

import kotlinx.coroutines.flow.Flow
import my.sdl.smarthome.remoteapp.domain.model.Room

interface RoomRepository {

    fun observeRooms(): Flow<List<Room>>

    suspend fun insert(room: Room)

    suspend fun delete(id: Long)

    suspend fun getRoomById(id: Long): Room?

    suspend fun getRoomIdByKey(key: String): Long
    suspend fun clearAllRooms()
}