package my.sdl.smarthome.remoteapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.sdl.smarthome.remoteapp.core.mqtt.MqttEvent
import my.sdl.smarthome.remoteapp.ui.SyncViewModel
import org.koin.mp.KoinPlatform

@Composable
fun SyncScreen(vm: SyncViewModel) {
    val vm: SyncViewModel = KoinPlatform.getKoin().get()

    val events = remember { mutableStateListOf<MqttEvent>() }
    LaunchedEffect(Unit) {
        vm.collectMqttEvent().collect { events.add(it) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        events.forEach { event ->
            Text(text = event.toString())
        }
    }
}