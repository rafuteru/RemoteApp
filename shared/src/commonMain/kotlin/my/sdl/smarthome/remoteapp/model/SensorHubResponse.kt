package my.sdl.smarthome.remoteapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorHubResponse(
    @SerialName("t")
    val t: Double = 0.0,

    @SerialName("h")
    val h: Double = 0.0,

    @SerialName("b")
    val b: Int = 0,

    @SerialName("p")
    var p: Int = -1
) {
    fun isPresenceAvailable(): Boolean = p == 1
}
