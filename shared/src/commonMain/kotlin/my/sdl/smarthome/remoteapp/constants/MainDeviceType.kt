package my.sdl.smarthome.remoteapp.constants;

enum class MainDeviceType(
    val code: Int,
    val deviceKey: String
) {
    LIGHT(0, "l"),
    SOCKET(1, "s"),
    AC(2, "a"),
    HIGH_CURRENT_SWITCH(3, "hc"),
    WATER_HEATER(4, "h"),
    SENSOR_HUB(5, "sh");

    companion object {
        private val map = entries.associateBy { it.code }
        fun fromCode(code: Int) = map[code]
    }
}
