package my.sdl.smarthome.remoteapp.data.db

import android.content.Context
import my.sdl.smarthome.remoteapp.data.db.storage.DeviceStorage
import my.sdl.smarthome.remoteapp.model.AuthenticatedUser
import java.util.Locale

class DeviceStorageAndroid(context: Context) : DeviceStorage {
    private val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)


    override fun saveUid(uid: String) {
        prefs.edit().putString(PREF_USER_ID, uid).apply()
    }

    override fun getUid(): String {
        return prefs.getString(PREF_USER_ID,null)!!
    }

    override fun getUsername(): String {
        return prefs.getString(PREF_USER_NAME,null)!!
    }

    override fun loggedIn(userData: AuthenticatedUser) {
        val editor = prefs.edit()
        editor.putBoolean(PREF_IS_LOGGED, true)
        editor.putString(PREF_USER_ID, userData.uid)
        editor.putString(PREF_USER_NAME, userData.username.lowercase(Locale.getDefault()))
        editor.apply()
    }

    override fun loggedOut() {
        val editor = prefs.edit()
        editor.remove(PREF_IS_LOGGED)
        editor.remove(PREF_USER_NAME)
        editor.remove(PREF_USER_ID)
        editor.apply()
    }

    override fun remove(key: String) : Boolean {
        return prefs.edit().remove(key).commit()
    }

    fun savePrefHaBtnState(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getPrefHaBtnState(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    companion object {
        private const val PREF_FILE_NAME = "rsh_shrd_pref"
        const val PREF_IS_LOGGED: String = "rsh_is_logged"
        const val PREF_USER_NAME: String = "rsh_user_name"
        const val PREF_USER_ID: String = "rsh_user_id"
    }
}