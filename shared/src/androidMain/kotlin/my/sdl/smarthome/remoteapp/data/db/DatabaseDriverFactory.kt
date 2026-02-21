package my.sdl.smarthome.remoteapp.data.db

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import my.sdl.smarthome.remoteapp.AppDatabase

actual class DatabaseDriverFactory(private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            "remoteapp.db"
        )
    }
}