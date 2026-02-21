package my.sdl.smarthome.remoteapp.core
import android.content.Context
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import my.sdl.smarthome.remoteapp.constants.AppConstants
import my.sdl.smarthome.remoteapp.core.mqtt.AwsConnection
import my.sdl.smarthome.remoteapp.core.mqtt.ConnectionStatus
import my.sdl.smarthome.remoteapp.core.mqtt.MqttEvent
import my.sdl.smarthome.remoteapp.core.mqtt.PingManager
import my.sdl.smarthome.remoteapp.data.db.DeviceStorageAndroid
import my.sdl.smarthome.remoteapp.data.db.storage.RuntimeStorage
import my.sdl.smarthome.remoteapp.data.db.storage.getAwsId
import my.sdl.smarthome.remoteapp.data.db.storage.getPubTopic
import my.sdl.smarthome.remoteapp.data.db.storage.getSubTopic
import my.sdl.smarthome.remoteapp.utils.AppLog

class AwsConnectionManagerAndroid(
    context: Context,
) : AwsConnection {
    private val _events = MutableSharedFlow<MqttEvent>()      // Internal events
    override val events: SharedFlow<MqttEvent> get() = _events
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

    init {
        connectRemote()
    }
    override fun publishData(topic: String, message: String) {
        if (!isConnected || message.isEmpty()) return
        AppLog.logger.d(tag = TAG) { "topic : $topic and message : $message" }
        // Todo: Check if its required, I think its for maintain state
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
                        postEvent(MqttEvent.StatusChanged(ConnectionStatus.CONNECTED))
                        publishData(AppConstants.DefinedTopics.SYNC_STATUS_REQ, AppConstants.DefinedTopics.SYNC_STATUS_REQ)
                    }

                    AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting, AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connecting -> {
                        pingManager.stopPinging()
                        isConnected = false
                        postEvent(MqttEvent.StatusChanged(ConnectionStatus.CONNECTING))
                    }

                    else -> {
                        pingManager.stopPinging()
                        isConnected = false
                        postEvent(MqttEvent.StatusChanged(ConnectionStatus.DISCONNECTED))
                    }
                }
            }
        } catch (e: Exception) {
            postEvent(MqttEvent.Error(e.message))
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
                when (splitTopic[0]) {
                    AppConstants.DefinedTopics.SYNC_ROOM_RES -> postEvent(MqttEvent.SyncRoomResponse(splitTopic[1]))
                    AppConstants.DefinedTopics.SYNC_DEVICE_RES -> postEvent(MqttEvent.SyncDeviceResponse(splitTopic[1]))
                    else -> {
                        splitTopic[0].let { RuntimeStorage.put(it, splitTopic[1]) }
                        postEvent(MqttEvent.InterceptedMessage(splitTopic[0], splitTopic[1]))
                    }
                }
            } catch (e: Exception) {
                postEvent(MqttEvent.Error(e.message))
            }
        }
    }

    private fun postEvent(event: MqttEvent) {
        CoroutineScope(Dispatchers.Main).launch {
            _events.emit(event)
        }
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
