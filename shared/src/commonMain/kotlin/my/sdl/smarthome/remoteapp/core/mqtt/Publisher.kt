package my.sdl.smarthome.remoteapp.core.mqtt

interface Publisher {
    fun publish(topic: String, payload: String)
}
