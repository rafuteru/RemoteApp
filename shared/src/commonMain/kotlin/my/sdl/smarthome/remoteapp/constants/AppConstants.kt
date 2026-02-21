package my.sdl.smarthome.remoteapp.constants

object AppConstants {
    const val EMPTY: String = ""
    const val COMMA: String = ","
    const val HASH: String = "#"

    object Action {
        const val ACTION_MQTT_STATUS: String = "ACTION_MQTT_STATUS"
        const val ACTION_INTERCEPTED_MESSAGE: String = "ACTION_INTERCEPTED_MESSAGE"
        const val ACTION_SYNC_DEVICES: String = "ACTION_SYNC_DEVICES"
        const val ACTION_SYNC_ROOMS: String = "ACTION_SYNC_ROOMS"
        const val ACTION_ERROR: String = "ACTION_ERROR"
    }

    object ConnectionStatus {
        const val STATUS_CONNECTING: Int = 0
        const val STATUS_DISCONNECTED: Int = 1
        const val STATUS_CONNECTED: Int = 2

        //        public static final int STATUS_RECONNECTING = 3;
        const val STATUS_UNKNOWN: Int = 4
    }

    object IntentConstant {
        const val PARAM_ID: String = "PARAM_ID"
        const val PARAM_STATUS: String = "PARAM_STATUS"
        const val PARAM_MESSAGE: String = "PARAM_MESSAGE"
        const val PARAM_DATA: String = "PARAM_DATA"
        const val PARAM_TOPIC: String = "PARAM_TOPIC"
        const val PARAM_FIRST_TIME_USER: String = "PARAM_FIRST_TIME_USER"
        const val PARAM_ROOM_ID: String = "PARAM_ROOM_ID"
        const val PARAM_ACTION_TYPE: String = "PARAM_ACTION_TYPE"
        const val PARAM_DEVICE_TYPE: String = "PARAM_DEVICE_TYPE"
        const val PARAM_SHOW_MASTER_BTN_LAYOUT: String = "PARAM_SHOW_MASTER_BTN_LAYOUT"
        const val PARAM_TITLE: String = "PARAM_TITLE"
        const val PARAM_DISPLAY_ROOM_NAME: String = "param_display_room_name"
        const val PARAM_KEY_ROOM_NAME: String = "param_key_room_name"
        const val PARAM_IS_LAYOUT_LINEAR: String = "PARAM_IS_LAYOUT_LINEAR"
    }

    object MessageType {
        const val ERROR_MESSAGE: Int = 0
        const val SUCCESS_MESSAGE: Int = 1
        const val WARNING_MESSAGE: Int = 2
        const val INFO_MESSAGE: Int = 3
        const val APP_LIGHT_MESSAGE: Int = 4
        const val APP_DARK_MESSAGE: Int = 5
        const val ACCENT_MESSAGE: Int = 6
    }

    object DefinedTopics {
        const val SYNC_ROOM_REQ: String = "SYNC_ROOM_REQ"
        const val SYNC_ROOM_RES: String = "SYNC_ROOM_RES"
        const val SYNC_DEVICE_REQ: String = "SYNC_DEVICE_REQ"
        const val SYNC_DEVICE_RES: String = "SYNC_DEVICE_RES"
        const val SYNC_STATUS_REQ: String = "SYNC_STATUS_REQ"
        const val R_MODE_REQUEST: String = "r_mode_request"
        const val PING_IAM_ALIVE: String = "ping_iam_alive"
    }


    object Millis {
        const val ONE_SECOND: Int = 1000
    }
}
