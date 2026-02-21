package my.sdl.smarthome.remoteapp.core.mqtt

import kotlinx.coroutines.flow.SharedFlow

// commonMain
interface AwsConnection {
    val events: SharedFlow<MqttEvent>
    val isConnected: Boolean  // public read-only

    fun publishData(topic: String, message: String) // public

    fun disconnect() // public
}
