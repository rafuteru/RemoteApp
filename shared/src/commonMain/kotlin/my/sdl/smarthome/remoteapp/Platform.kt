package my.sdl.smarthome.remoteapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform