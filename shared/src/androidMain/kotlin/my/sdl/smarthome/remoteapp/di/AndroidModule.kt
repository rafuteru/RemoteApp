package my.sdl.smarthome.remoteapp.di

import my.sdl.smarthome.remoteapp.core.AwsConnectionManagerAndroid
import my.sdl.smarthome.remoteapp.core.mqtt.AwsConnection
import my.sdl.smarthome.remoteapp.data.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<AwsConnection> { AwsConnectionManagerAndroid(get()) } // only depends on context
}
