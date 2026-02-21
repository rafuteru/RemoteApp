package my.sdl.smarthome.remoteapp.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.data.db.toDomain
import my.sdl.smarthome.remoteapp.data.db.toEntity
import my.sdl.smarthome.remoteapp.domain.model.Room
import my.sdl.smarthome.remoteapp.data.repository.RoomRepository

class RoomRepositoryImpl(
    private val database: AppDatabase
) : RoomRepository {

    private val queries = database.roomQueries

    override fun observeRooms(): Flow<List<Room>> {
        return queries.selectAllRooms()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insert(room: Room) {
        val entity = room.toEntity()
        queries.insertRoom(
            room_key = entity.room_key,
            name = entity.name
        )
    }

    override suspend fun delete(id: Long) {
        queries.deleteRoom(id)
    }

    override suspend fun getRoomById(id: Long): Room? {
        return queries.selectRoomById(id)
            .executeAsOneOrNull()
            ?.toDomain()
    }
}