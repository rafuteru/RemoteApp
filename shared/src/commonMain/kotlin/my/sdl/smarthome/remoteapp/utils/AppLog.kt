package my.sdl.smarthome.remoteapp.utils

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.Severity

object AppLog {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Debug,
        )
    )
}