package my.sdl.smarthome.remoteapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import my.sdl.smarthome.remoteapp.core.mqtt.AwsConnection
import my.sdl.smarthome.remoteapp.core.mqtt.MqttEvent
import my.sdl.smarthome.remoteapp.data.db.dto.RoomDto
import my.sdl.smarthome.remoteapp.domain.repository.DeviceRepository
import my.sdl.smarthome.remoteapp.domain.repository.RoomRepository
import my.sdl.smarthome.remoteapp.domain.model.Room
import my.sdl.smarthome.remoteapp.utils.AppLog

const val sampleJson = """
{
    "roomKey": "living_room",
    "name": "Living Room",
    "devices": [
        {"code": 0, "switchId": "A1", "channel": 0},
        {"code": 1, "switchId": "B2", "channel": 1},
        {"code": 2, "switchId": "C3", "channel": 2}
    ]
}
"""

class SyncViewModel(
    private val awsConnection: AwsConnection,
    private val roomRepo: RoomRepository,
    private val deviceRepo: DeviceRepository
) : ViewModel() {

    val roomDto = Json.decodeFromString<RoomDto>(sampleJson)
    suspend fun collectMqttEvent(): SharedFlow<MqttEvent> {
        return awsConnection.events
    }

    init {

        viewModelScope.launch {
            roomRepo.getRoomById(1)?.let {
                AppLog.logger.d { "" }
            } ?: insertRoomFromServer(roomDto)
        }
    }

    fun insertRoomFromServer(roomDto: RoomDto) {
        viewModelScope.launch {
            // 1️⃣ Clear old data
//            roomRepo.clearAllRooms()
            // 1️⃣ Insert room (get roomId if needed)
            roomRepo.insert(
                Room(
                    id = 0L, // will be auto-generated
                    roomKey = roomDto.roomKey,
                    name = roomDto.name
                )
            )

            val roomId = roomRepo.getRoomIdByKey(roomDto.roomKey)

            // 2️⃣ Insert devices + supported_device
            deviceRepo.insertDevices(roomId, roomDto.devices)
        }
    }
}