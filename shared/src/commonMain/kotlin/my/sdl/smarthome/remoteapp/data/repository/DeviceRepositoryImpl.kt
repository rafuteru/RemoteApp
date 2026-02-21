package my.sdl.smarthome.remoteapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.constants.SupportDeviceType
import my.sdl.smarthome.remoteapp.data.db.dto.DeviceDto
import my.sdl.smarthome.remoteapp.data.db.entity.toDomain
import my.sdl.smarthome.remoteapp.domain.model.Device
import my.sdl.smarthome.remoteapp.domain.repository.DeviceRepository
import org.koin.core.qualifier.named
class DeviceRepositoryImpl(
    database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher
) : DeviceRepository {

    private val queries = database.deviceQueries
    private val supportedDeviceQueries = database.supported_deviceQueries

    override suspend fun insertDevice(device: Device) {
        // IO dispatcher for DB writes
        withContext(ioDispatcher) {
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
        // CPU-bound transformation on Default dispatcher
        return queries.selectDevicesByRoom(roomId)
            .asFlow()
            .mapToList(ioDispatcher) // reads still on IO
            .map { list ->
                withContext(defaultDispatcher) { list.map { it.toDomain() } }
            }
    }

    override suspend fun deleteDevice(deviceId: Long) {
        withContext(ioDispatcher) {
            queries.deleteDevice(
                id = deviceId,
            )
        }
    }

    override suspend fun insertDevices(roomId: Long, devices: List<DeviceDto>) {
        withContext(ioDispatcher) {
            devices.forEach { deviceDto ->
                // 1️⃣ Insert device
                queries.insertDevice(
                    room_id = roomId,
                    name = "Device-${deviceDto.switchId}", // or your naming logic
                    device_code = deviceDto.code.toLong(),
                    switch_id = deviceDto.switchId,
                    channel_count = deviceDto.channel.toLong(), // channel index → count
                    qos = 0L,
                    is_retain = 0
                )

                // 2️⃣ Get the auto-generated device ID
                val deviceId = queries.getLastInsertedDeviceId().executeAsOne()

                // 3️⃣ Insert into supported_device table (fixed PC)
                supportedDeviceQueries.insertSupportedDevice(
                    device_id = deviceId,
                    code = SupportDeviceType.PC.code.toLong() // always PC
                )
            }
        }
    }
}