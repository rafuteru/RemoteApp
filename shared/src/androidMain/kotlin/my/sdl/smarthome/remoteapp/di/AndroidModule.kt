package my.sdl.smarthome.remoteapp.di

import my.sdl.smarthome.remoteapp.data.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single { DatabaseDriverFactory(androidContext()) }
}