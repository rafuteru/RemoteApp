package my.sdl.smarthome.remoteapp.nav

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import my.sdl.smarthome.remoteapp.ui.SyncViewModel
import my.sdl.smarthome.remoteapp.ui.SyncScreen
import org.koin.mp.KoinPlatform

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        // Home screen
        composable("home") {
            Button(onClick = { navController.navigate("sync") }) {
                Text("Open Sync Screen")
            }
        }

        // Sync screen
        composable("sync") {
            // Get shared ViewModel and AWS connection via DI
            val vm: SyncViewModel = KoinPlatform.getKoin().get()

            SyncScreen(vm = vm)
        }
    }
}