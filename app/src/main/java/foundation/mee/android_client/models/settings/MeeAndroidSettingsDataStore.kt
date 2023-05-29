package foundation.mee.android_client.models.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import foundation.mee.android_client.controller.biometry.BiometryState
import kotlinx.coroutines.flow.map

const val MERCHANT_DATASTORE ="mee_android_settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = MERCHANT_DATASTORE)

class MeeAndroidSettingsDataStore (val context: Context) {

    companion object {
        val BIOMETRIC_AUTH_ENABLED = stringPreferencesKey("BIOMETRIC_AUTH_ENABLED")
        val INITIAL_FLOW_DONE = booleanPreferencesKey("INITIAL_FLOW_DONE")
    }

    suspend fun saveBiometricAuthSetting(flag: BiometryState) {
        context.dataStore.edit {
            it[BIOMETRIC_AUTH_ENABLED] = flag.toString()
        }
    }

    suspend fun saveInitialFlowDoneSetting(flag: Boolean) {
        context.dataStore.edit {
            it[INITIAL_FLOW_DONE] = flag
        }
    }

    fun getBiometricAuthEnabledSetting() = context.dataStore.data.map {
        val stringValue = it[BIOMETRIC_AUTH_ENABLED]
        if (stringValue is String) {
            BiometryState.valueOf(stringValue)
        } else {
            BiometryState.unsaved
        }
    }

    fun getInitialFlowDoneSetting() = context.dataStore.data.map {
        it[INITIAL_FLOW_DONE]?:false
    }
}
