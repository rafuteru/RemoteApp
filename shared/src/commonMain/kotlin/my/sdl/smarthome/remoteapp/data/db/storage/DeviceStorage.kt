package my.sdl.smarthome.remoteapp.data.db.storage

import my.sdl.smarthome.remoteapp.model.AuthenticatedUser
import kotlin.random.Random

interface DeviceStorage {
    fun saveUid(uid: String)
    fun getUid(): String
    fun getUsername(): String
    fun loggedIn(userData: AuthenticatedUser)
    fun loggedOut()
    fun remove(key: String) : Boolean
}

fun DeviceStorage.getAwsId(): String {
    return "remote_${getUid()}_${Random.nextInt(1000)}"
}

fun DeviceStorage.getPubTopic(): String {
    return "local_${getUid()}"
}

fun DeviceStorage.getSubTopic(): String {
    return "remote_${getUid()}"
}