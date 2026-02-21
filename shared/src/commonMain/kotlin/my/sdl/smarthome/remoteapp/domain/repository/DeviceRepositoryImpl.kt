package my.sdl.smarthome.remoteapp.domain.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.data.db.toDomain
import my.sdl.smarthome.remoteapp.data.repository.DeviceRepository
import my.sdl.smarthome.remoteapp.domain.model.Device
class DeviceRepositoryImpl(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher
) : DeviceRepository {

    private val queries = database.deviceQueries
    override suspend fun insertDevice(device: Device) {
        withContext(dispatcher) {
            queries.insertDevice(
                room_id = device.roomId,
                name = device.name,
                device_code = device.type.code.toLong(),
                switch_id = device.switchId,
                channel_count = device.channelCount.toLong(),
                qos = device.qos.toLong(),
                is_retain = if (device.isRetain) 1 else 0
            )
        }
    }

    override fun observeDevices(roomId: Long): Flow<List<Device>> {
        return database.deviceQueries
            .selectDevicesByRoom(roomId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun deleteDevice(deviceId: Long) {
        withContext(dispatcher) {
            queries.deleteDevice(deviceId)
        }
    }
}