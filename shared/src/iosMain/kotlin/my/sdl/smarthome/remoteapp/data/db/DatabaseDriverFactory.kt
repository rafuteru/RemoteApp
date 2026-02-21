package my.sdl.smarthome.remoteapp.data.db

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import my.sdl.smarthome.remoteapp.AppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            AppDatabase.Schema,
            "remoteapp.db"
        )
    }
}