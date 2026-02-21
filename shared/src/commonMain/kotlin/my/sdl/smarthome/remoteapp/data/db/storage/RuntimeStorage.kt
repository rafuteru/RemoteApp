package my.sdl.smarthome.remoteapp.data.db.storage

object RuntimeStorage {
    private val values: MutableMap<String, String> = mutableMapOf()

    fun put(key: String, value: String? = null) {
        value?.let {
            values[key] = value
        } ?: remove(key)
    }

    fun get(key: String): String? = values[key]

    fun remove(key: String) {
        values.remove(key)
    }

    fun clear() {
        values.clear()
    }

    fun contains(key: String): Boolean = values.containsKey(key)
}