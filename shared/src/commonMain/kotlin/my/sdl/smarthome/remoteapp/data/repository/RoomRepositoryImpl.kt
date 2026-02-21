package my.sdl.smarthome.remoteapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.data.db.entity.toDomain
import my.sdl.smarthome.remoteapp.data.db.entity.toEntity
import my.sdl.smarthome.remoteapp.domain.model.Room
import my.sdl.smarthome.remoteapp.domain.repository.RoomRepository

class RoomRepositoryImpl(
    database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : RoomRepository {

    private val queries = database.roomQueries

    override fun observeRooms(): Flow<List<Room>> {
        return queries.selectAllRooms()
            .asFlow()
            .mapToList(ioDispatcher) // reading on IO
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insert(room: Room) {
        withContext(ioDispatcher) {
            val entity = room.toEntity()
            queries.insertRoom(
                room_key = entity.room_key,
                name = entity.name
            )
        }
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            queries.deleteRoom(id)
        }
    }

    override suspend fun getRoomById(id: Long): Room? {
        return withContext(ioDispatcher) {
            queries.selectRoomById(id)
                .executeAsOneOrNull()
                ?.toDomain()
        }
    }

    override suspend fun getRoomIdByKey(key: String): Long {
        return withContext(ioDispatcher) {
            queries.getRoomIdByKey(key)
                .executeAsOne()
        }
    }

    override suspend fun clearAllRooms() {
        return withContext(ioDispatcher) {
            queries.deleteAllDevices()
        }
    }
}