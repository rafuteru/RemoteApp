package my.sdl.smarthome.remoteapp.core
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions
import my.sdl.smarthome.remoteapp.constants.AppConstants
import my.sdl.smarthome.remoteapp.core.mqtt.AwsConnection
import my.sdl.smarthome.remoteapp.core.mqtt.PingManager
import my.sdl.smarthome.remoteapp.data.db.DeviceStorageAndroid
import my.sdl.smarthome.remoteapp.data.db.storage.RuntimeStorage
import my.sdl.smarthome.remoteapp.data.db.storage.getAwsId
import my.sdl.smarthome.remoteapp.data.db.storage.getPubTopic
import my.sdl.smarthome.remoteapp.data.db.storage.getSubTopic

class AwsConnectionManagerAndroid(context: Context) : AwsConnection {

    private val appContext = context.applicationContext
    private val deviceStorageAndroid = DeviceStorageAndroid(appContext)
    private val credentialsProvider: CognitoCachingCredentialsProvider by lazy {
        CognitoCachingCredentialsProvider(
            appContext,
            COGNITO_POOL_ID,  // Identity pool ID
            MY_REGION // Region
        )
    }
    private val pingManager by lazy {
        PingManager(AwsPublisher(this))
    }
    private val awsIotMqttManager by lazy {
        AWSIotMqttManager(deviceStorageAndroid.getAwsId(), CUSTOMER_SPECIFIC_ENDPOINT)
    }

    init {
        awsIotMqttManager.setAutoResubscribe(false)
    }
    override var isConnected: Boolean = false

    override fun publishData(topic: String, message: String) {
        if (!isConnected || message.isEmpty()) return
//        BasicSharedPref.setMapValue(
//            AppConstants.SUBSCRIPTION_PREFIX + AppConstants.SLASH + topic,
//            message
//        )
        awsIotMqttManager.publishData(
            (topic + REMOTE_PAYLOD_SEPERATOR + message).toByteArray(),
            deviceStorageAndroid.getPubTopic(),
            AWSIotMqttQos.QOS0
        )
    }

    override fun disconnect() {
        awsIotMqttManager.disconnect()
    }

    fun connectRemote() {
        try {
            awsIotMqttManager.connect(credentialsProvider) { status: AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus?, _: Throwable? ->
                when (status) {
                    AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected -> {
                        pingManager.startPinging(15000)
                        isConnected = true
                        subscribeToTopic(deviceStorageAndroid.getSubTopic())
                        sendBroadCast(
                            AppConstants.Action.ACTION_MQTT_STATUS,
                            AppConstants.IntentConstant.PARAM_STATUS,
                            AppConstants.ConnectionStatus.STATUS_CONNECTED
                        )
                        publishData(AppConstants.DefinedTopics.SYNC_STATUS_REQ, AppConstants.DefinedTopics.SYNC_STATUS_REQ)
                    }

                    AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting, AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting -> {
                        pingManager.stopPinging()
                        isConnected = false
                        sendBroadCast(
                            AppConstants.Action.ACTION_MQTT_STATUS,
                            AppConstants.IntentConstant.PARAM_STATUS,
                            AppConstants.ConnectionStatus.STATUS_CONNECTING
                        )
                    }

                    else -> {
                        pingManager.stopPinging()
                        isConnected = false
                        sendBroadCast(
                            AppConstants.Action.ACTION_MQTT_STATUS,
                            AppConstants.IntentConstant.PARAM_STATUS,
                            AppConstants.ConnectionStatus.STATUS_DISCONNECTED
                        )
                    }
                }
            }
        } catch (e: Exception) {
            sendBroadCast(
                AppConstants.Action.ACTION_ERROR,
                AppConstants.IntentConstant.PARAM_MESSAGE,
                e.message
            )
        }
    }

    private fun subscribeToTopic(subTopic: String) {
        awsIotMqttManager.subscribeToTopic(
            subTopic, AWSIotMqttQos.QOS0
        ) { _: String?, data: ByteArray? ->
            try {
                val splitTopic: Array<String>? =
                    data?.decodeToString()
                        ?.split(REMOTE_PAYLOD_SEPERATOR.toRegex())
                        ?.dropLastWhile { it.isEmpty() }
                        ?.toTypedArray()

                if (splitTopic == null || splitTopic[1].isEmpty()) return@subscribeToTopic
                if (splitTopic[0].equals(AppConstants.DefinedTopics.SYNC_ROOM_RES, ignoreCase = true)) {
                    sendBroadCast(
                        AppConstants.Action.ACTION_SYNC_ROOMS,
                        AppConstants.IntentConstant.PARAM_DATA,
                        splitTopic[1]
                    )
                } else if (splitTopic[0].equals(
                        AppConstants.DefinedTopics.SYNC_DEVICE_RES,
                        ignoreCase = true
                    )
                ) {
                    sendBroadCast(
                        AppConstants.Action.ACTION_SYNC_DEVICES,
                        AppConstants.IntentConstant.PARAM_DATA,
                        splitTopic[1]
                    )
                } else {
                    splitTopic[0]?.let { RuntimeStorage.put(it, splitTopic[1]) }
                    sendBroadCast(
                        AppConstants.Action.ACTION_INTERCEPTED_MESSAGE,
                        AppConstants.IntentConstant.PARAM_TOPIC,
                        splitTopic[0]
                    )
                }
            } catch (e: Exception) {
                sendBroadCast(
                    AppConstants.Action.ACTION_ERROR,
                    AppConstants.IntentConstant.PARAM_MESSAGE,
                    e.message
                )
            }
        }
    }

    private fun sendBroadCast(action: String, key: String, value: String?) {
        val intent = Intent(action)
        intent.putExtra(key, value)
        LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent)
    }

    //    private Intent getIntent(String action) {
    //        Intent intent = new Intent(action);
    //        return intent;
    //    }
    //
    //    private void sendBroadCast(Intent intent) {
    //        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    //    }
    private fun sendBroadCast(action: String, key: String, value: Int) {
        val intent = Intent(action)
        intent.putExtra(key, value)
        LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent)
    }

    companion object {
        private const val TAG = "AwsConnectionManager"
        private const val CUSTOMER_SPECIFIC_ENDPOINT =
            "a737e058vrwvh-ats.iot.eu-central-1.amazonaws.com"

        // Cognito pool ID. For this app, pool needs to be unauthenticated pool with
        // AWS IoT permissions.
        private const val COGNITO_POOL_ID = "eu-central-1:6888e504-258e-4a26-b81f-5f1529450ce2"
        private const val REMOTE_PAYLOD_SEPERATOR = ";;"
        private val MY_REGION = Regions.EU_CENTRAL_1
    }
}
