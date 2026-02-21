package my.sdl.smarthome.remoteapp.core.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.ui.SyncViewModel
import my.sdl.smarthome.remoteapp.data.db.DatabaseDriverFactory
import my.sdl.smarthome.remoteapp.domain.repository.DeviceRepository
import my.sdl.smarthome.remoteapp.domain.repository.RoomRepository
import my.sdl.smarthome.remoteapp.data.repository.DeviceRepositoryImpl
import my.sdl.smarthome.remoteapp.data.repository.RoomRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    // Now this works because AwsConnection is already defined in Android module
    single { SyncViewModel(get(), get(), get()) }         // injects AwsConnection
    single { AppDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single(named("io")) { Dispatchers.IO }
    single(named("default")) { Dispatchers.Default }
    single<DeviceRepository> { DeviceRepositoryImpl(get(), get(named("io")), get(named("default"))) }

    single<RoomRepository> { RoomRepositoryImpl(get(), get(named("io"))) }
}