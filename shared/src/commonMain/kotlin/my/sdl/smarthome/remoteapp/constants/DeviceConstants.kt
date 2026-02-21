package my.sdl.smarthome.remoteapp.constants

object DeviceConstants {
    object DeviceType {
        const val DT_LIGHT: Int = 0
        const val DT_SOCKET: Int = 1
        const val DT_AC: Int = 2
        const val DT_PC: Int = 3
        const val DT_WATER_HEATER: Int = 11
        const val DT_HIGH_CURRENT_SWITCH: Int = 10
        const val DT_SENSOR_HUB: Int = 16
        const val DT_TV_REMOTE: Int = 6
        const val DT_AC_REMOTE: Int = 9 //        public static final int DT_HEADER = 999
    }

    //
    object DeviceCode {
        const val DC_LIGHT: String = "l"
        const val DC_HIGH_CURRENT_SWITCH: String = "hc"
        const val DC_SOCKET: String = "s"
        const val DC_AIR_CONDITIONER: String = "a"
        const val DC_WATER_HEATER: String = "h"
        const val DC_SENSOR_HUB: String = "sh"
    }

    //
    object DevicePostFix {
        const val DPF_POWER_METER: String = "w"
        const val DPF_REMOTE: String = "ir"
    }

    object CHANNEL_COUNT {
        const val COUNT_ZERO: Int = 0
        const val COUNT_ONE: Int = 1
        const val COUNT_TWO: Int = 2
        const val COUNT_THREE: Int = 3
    }

    object Channels {
        const val C1: String = "c1"
        const val C2: String = "c2"
        const val C3: String = "c3"
    }

    object AcModes {
        const val AUTO: String = "auto"
        const val COOL: String = "cool"
        const val DRY: String = "dry"
        const val FAN: String = "fan"
        const val HEAT: String = "heat"
    }
}
