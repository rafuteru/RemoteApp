package my.sdl.smarthome.remoteapp.data.repository

import kotlinx.coroutines.flow.Flow
import my.sdl.smarthome.remoteapp.domain.model.Device

interface DeviceRepository {

    suspend fun insertDevice(device: Device)

    fun observeDevices(roomId: Long): Flow<List<Device>>

    suspend fun deleteDevice(deviceId: Long)
}
