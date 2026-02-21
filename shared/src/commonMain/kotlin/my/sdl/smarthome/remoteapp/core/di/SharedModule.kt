package my.sdl.smarthome.remoteapp.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import my.sdl.smarthome.remoteapp.AppDatabase
import my.sdl.smarthome.remoteapp.data.db.DatabaseDriverFactory
import my.sdl.smarthome.remoteapp.data.repository.DeviceRepository
import my.sdl.smarthome.remoteapp.domain.repository.DeviceRepositoryImpl
import org.koin.dsl.module

val sharedModule = module {

    // Database
    single {
        AppDatabase(
            get<DatabaseDriverFactory>().createDriver()
        )
    }

    // Dispatchers (VERY IMPORTANT)
    single<CoroutineDispatcher> { Dispatchers.Default }

    // Repositories
    single<DeviceRepository> {
        DeviceRepositoryImpl(
            database = get(),
            dispatcher = get()
        )
    }
}