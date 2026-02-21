package my.sdl.smarthome.remoteapp.constants;

enum class Channel(val value: String) {
    C1("c1"),
    C2("c2"),
    C3("c3");

    companion object {
        fun fromIndex(index: Int): Channel? =
            entries.getOrNull(index - 1)
    }
}
