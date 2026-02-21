package my.sdl.smarthome.remoteapp.constants;

enum class SupportDeviceType(
    val code: Int,
    val key: String
) {
    PC(0,"pc"),
    REMOTE(1,"ir");

    companion object {
        private val map = SupportDeviceType.entries.associateBy { it.code }
        fun fromCode(type: Int) = map[type]
    }
}