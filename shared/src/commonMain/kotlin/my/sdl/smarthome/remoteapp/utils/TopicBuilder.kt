package my.sdl.smarthome.remoteapp.utils

import my.sdl.smarthome.remoteapp.constants.MainDeviceType
import my.sdl.smarthome.remoteapp.constants.SupportDeviceType
import my.sdl.smarthome.remoteapp.domain.model.Device

object TopicBuilder {

    private const val SUB_PREFIX = "s"

    /* -----------------------------
     * Base path (publish format)
     * l/room/sw1
     * ----------------------------- */
    private fun basePath(
        mainType: MainDeviceType,
        roomKey: String,
        switchId: String
    ): String {
        return "${mainType.deviceKey}/$roomKey/$switchId"
    }

    /* -----------------------------
     * Publish main channel
     * l/room/sw1/1
     * ----------------------------- */
    fun pubTopic(
        mainType: MainDeviceType,
        roomKey: String,
        switchId: String,
        channel: Int
    ): String {
        return "${basePath(mainType, roomKey, switchId)}/$channel"
    }

    /* -----------------------------
     * Publish support
     * l/room/sw1/pc
     * ----------------------------- */
    fun supportPubTopic(
        mainType: MainDeviceType,
        roomKey: String,
        switchId: String,
        supportType: SupportDeviceType
    ): String {
        return "${basePath(mainType, roomKey, switchId)}/${supportType.key}"
    }

    /* -----------------------------
     * Subscribe wrapper
     * s/l/room/sw1/1
     * ----------------------------- */
    fun subTopic(pubTopic: String): String {
        return "$SUB_PREFIX/$pubTopic"
    }

    fun Device.subTopic(): String {
        return subTopic(pubTopic(MainDeviceType.fromCode(0)!!, "roomKey", switchId, channelCount))
    }

    fun Device.pubTopic(): String {
        return pubTopic(MainDeviceType.fromCode(0)!!, "roomKey", switchId, channelCount)
    }
}

