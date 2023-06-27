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
        val INITIAL_FLOW_DONE = booleanPreferencesKey("INITIAL_FLOW_DONE")
    }

    suspend fun saveInitialFlowDoneSetting(flag: Boolean) {
        context.dataStore.edit {
            it[INITIAL_FLOW_DONE] = flag
        }
    }

    fun getInitialFlowDoneSetting() = context.dataStore.data.map {
        it[INITIAL_FLOW_DONE]?:false
    }
}
