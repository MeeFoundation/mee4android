package foundation.mee.android_client.models.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val MERCHANT_DATASTORE ="mee_android_settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MERCHANT_DATASTORE)

class MeeAndroidSettingsDataStore (val context: Context) {

    companion object {
        val BIOMETRIC_AUTH_ASKED = booleanPreferencesKey("BIOMETRIC_AUTH_ASKED")
        val BIOMETRIC_AUTH_ENABLED = booleanPreferencesKey("BIOMETRIC_AUTH_ENABLED")
    }

    suspend fun saveBiometricAuthAskedSetting(flag: Boolean) {
        context.dataStore.edit {
            it[BIOMETRIC_AUTH_ASKED] = flag
        }
    }

    suspend fun saveBiometricAuthSetting(flag: Boolean) {
        context.dataStore.edit {
            it[BIOMETRIC_AUTH_ENABLED] = flag
        }
    }

    fun getBiometricAuthAskedSetting() = context.dataStore.data.map {
        it[BIOMETRIC_AUTH_ASKED]?:false
    }

    fun getBiometricAuthEnabledSetting() = context.dataStore.data.map {
        it[BIOMETRIC_AUTH_ENABLED]?:false
    }
}
