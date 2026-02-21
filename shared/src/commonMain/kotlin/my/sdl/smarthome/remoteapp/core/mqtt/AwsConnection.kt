package my.sdl.smarthome.remoteapp.core.mqtt

// commonMain
interface AwsConnection {
    val isConnected: Boolean  // public read-only

    fun publishData(topic: String, message: String) // public

    fun disconnect() // public
}
