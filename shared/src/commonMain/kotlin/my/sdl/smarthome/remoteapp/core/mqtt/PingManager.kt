package my.sdl.smarthome.remoteapp.core.mqtt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PingManager(private val publisher: Publisher) {

    private var job: Job? = null

    fun startPinging(delayMs: Long = 15000L) {
        stopPinging() // stop any existing ping

        job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
//                publisher.publish(PING_IAM_ALIVE, PING_IAM_ALIVE) // TODO Ping service to make connection alive
                delay(delayMs)
            }
        }
    }

    fun stopPinging() {
        job?.cancel()
        job = null
    }
}
