package my.sdl.smarthome.remoteapp.core.mqtt

sealed class MqttEvent {
    data class SyncRoomResponse(val data: String) : MqttEvent()
    data class SyncDeviceResponse(val data: String) : MqttEvent()
    data class InterceptedMessage(val topic: String, val data: String) : MqttEvent()
    data class Error(val message: String?) : MqttEvent()
    data class StatusChanged(val status: ConnectionStatus) : MqttEvent()
}