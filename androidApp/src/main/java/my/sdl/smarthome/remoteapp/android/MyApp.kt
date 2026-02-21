package my.sdl.smarthome.remoteapp.android

import android.app.Application
import my.sdl.smarthome.remoteapp.core.di.sharedModule
import my.sdl.smarthome.remoteapp.data.db.DeviceStorageAndroid
import my.sdl.smarthome.remoteapp.di.androidModule
import my.sdl.smarthome.remoteapp.model.AuthenticatedUser
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DeviceStorageAndroid(applicationContext).loggedIn(
            AuthenticatedUser("a","a") // TODO For testing
        )
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(androidModule, sharedModule))
        }
    }
}