package my.sdl.smarthome.remoteapp.utils

import my.sdl.smarthome.remoteapp.constants.AppConstants.EMPTY
import kotlin.jvm.JvmStatic

object JsonShortner {
    const val DEFAULT_NETTY_MAX_BYTES_IN_MESSAGE: Int = 8092
    private const val key_a = "#a"
    private const val value_a = "channel_count"
    private const val value_a_analytics = "createdDate"
    private const val key_b = "#b"
    private const val value_b = "device_mode"
    private const val value_b_analytics = "topic"
    private const val key_c = "#c"
    private const val value_c = "device_name"
    private const val value_c_analytics = "value"
    private const val key_d = "#d"
    private const val value_d = "parent_device_type"
    private const val key_e = "#e"
    private const val value_e = "did"
    private const val key_f = "#f"
    private const val value_f = "f_rid"
    private const val key_g = "#g"
    private const val value_g = "header_id"
    private const val key_h = "#h"
    private const val value_h = "is_retain"
    private const val key_i = "#i"
    private const val value_i = "layout_type"
    private const val key_j = "#j"
    private const val value_j = "device_type"
    private const val key_k = "#k"
    private const val value_k = "payload_off"
    private const val key_l = "#l"
    private const val value_l = "payload_on"
    private const val key_m = "#m"
    private const val value_m = "qos"
    private const val key_n = "#n"
    private const val value_n = "range_limit"
    private const val key_o = "#o"
    private const val value_o = "range_to"
    private const val key_p = "#p"
    private const val value_p = "switch_id"
    private const val key_q = "#q"
    private const val value_q = "Light 1"
    private const val key_r = "#r"
    private const val value_r = "Light 2"
    private const val key_s = "#s"
    private const val value_s = "Light 3"
    private const val key_t = "#t"
    private const val value_t = "Power Socket"
    private const val key_u = "#u"
    private const val value_u = "Air Conditioner"
    private const val key_v = "#v"
    private const val value_v = "High Current Switch"
    private const val key_w = "#w"
    private const val value_w = "Water Heater"
    private const val key_x = "#x"
    private const val value_x = "deviceCount"
    private const val key_y = "#y"
    private const val value_y = "rid"
    private const val key_z = "#z"
    private const val value_z = "keyRoomName"
    private const val key_ab = "#ab"
    private const val value_ab = "displayRoomName"

    fun getEncryptedStringForDevice(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(value_a.toRegex(), key_a)
            .replace(value_b.toRegex(), key_b)
            .replace(value_c.toRegex(), key_c)
            .replace(value_d.toRegex(), key_d)
            .replace(value_e.toRegex(), key_e)
            .replace(value_f.toRegex(), key_f)
            .replace(value_g.toRegex(), key_g)
            .replace(value_h.toRegex(), key_h)
            .replace(value_i.toRegex(), key_i)
            .replace(value_j.toRegex(), key_j)
            .replace(value_k.toRegex(), key_k)
            .replace(value_l.toRegex(), key_l)
            .replace(value_m.toRegex(), key_m)
            .replace(value_n.toRegex(), key_n)
            .replace(value_o.toRegex(), key_o)
            .replace(value_p.toRegex(), key_p)
            .replace(value_q.toRegex(), key_q)
            .replace(value_r.toRegex(), key_r)
            .replace(value_s.toRegex(), key_s)
            .replace(value_t.toRegex(), key_t)
            .replace(value_u.toRegex(), key_u)
            .replace(value_v.toRegex(), key_v)
            .replace(value_w.toRegex(), key_w)
    }

    @JvmStatic
    fun getDecryptedStringForDevice(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(key_a.toRegex(), value_a)
            .replace(key_b.toRegex(), value_b)
            .replace(key_c.toRegex(), value_c)
            .replace(key_d.toRegex(), value_d)
            .replace(key_e.toRegex(), value_e)
            .replace(key_f.toRegex(), value_f)
            .replace(key_g.toRegex(), value_g)
            .replace(key_h.toRegex(), value_h)
            .replace(key_i.toRegex(), value_i)
            .replace(key_j.toRegex(), value_j)
            .replace(key_k.toRegex(), value_k)
            .replace(key_l.toRegex(), value_l)
            .replace(key_m.toRegex(), value_m)
            .replace(key_n.toRegex(), value_n)
            .replace(key_o.toRegex(), value_o)
            .replace(key_p.toRegex(), value_p)
            .replace(key_q.toRegex(), value_q)
            .replace(key_r.toRegex(), value_r)
            .replace(key_s.toRegex(), value_s)
            .replace(key_t.toRegex(), value_t)
            .replace(key_u.toRegex(), value_u)
            .replace(key_v.toRegex(), value_v)
            .replace(key_w.toRegex(), value_w)
    }

    fun getEncryptedStringForRoom(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(value_x.toRegex(), key_x)
            .replace(value_y.toRegex(), key_y)
            .replace(value_z.toRegex(), key_z)
            .replace(value_ab.toRegex(), key_ab)
    }

    @JvmStatic
    fun getDecryptedStringForRoom(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(key_x.toRegex(), value_x)
            .replace(key_y.toRegex(), value_y)
            .replace(key_z.toRegex(), value_z)
            .replace(key_ab.toRegex(), value_ab)
    }

    fun getEncryptedStringForAnalytics(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(value_a_analytics.toRegex(), key_a)
            .replace(value_b_analytics.toRegex(), key_b)
            .replace(value_c_analytics.toRegex(), key_c)
    }

    fun getDecryptedStringForAnalytics(json: String): String {
        if (!validate(json)) return EMPTY
        return json.replace(key_a.toRegex(), value_a_analytics)
            .replace(key_b.toRegex(), value_b_analytics)
            .replace(key_c.toRegex(), value_c_analytics)
    }

    private fun validate(json: String?): Boolean {
        return !json.isNullOrEmpty()
    }
}
