package foundation.mee.android_client

import android.app.KeyguardManager
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import foundation.mee.android_client.BuildConfig.GOOGLE_API_KEY
import foundation.mee.android_client.controller.biometry.BiometryHandler
import foundation.mee.android_client.effects.OnLifecycleEvent
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.MeeNavGraph
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.service.ReferrerClient
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.MeeWhiteScreen
import foundation.mee.android_client.views.initial_flow.InitAgentErrorMessage
import foundation.mee.android_client.views.initial_flow.MigrationErrorMessage
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val keyguard = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)

        val meeAgentStore: MeeAgentViewModel by viewModels()
        val initAgentResult = meeAgentStore.initMeeAgent()

        setContent {
            when (initAgentResult.type) {
                Result.Type.SUCCESS -> ContentOnInitSuccess(keyguard)
                Result.Type.MIGRATION_ERROR -> MigrationErrorMessage()
                Result.Type.INIT_AGENT_ERROR -> InitAgentErrorMessage()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val model: NavViewModel by viewModels()
        val meeAgentViewModel: MeeAgentViewModel by viewModels()
        if (intent?.data?.scheme == "com.googleusercontent.apps.${GOOGLE_API_KEY}") {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    meeAgentViewModel.meeAgentStore.createGoogleConnection(intent.dataString!!)
                }
            }
        } else {
            try {
                model.navigator.navController.handleDeepLink(intent)
            } catch (e: java.lang.Exception) {
                Log.e("onNewIntent", e.message.orEmpty())
            }
        }
    }

}

@Composable
fun ContentOnInitSuccess(keyguard: KeyguardManager) {
    val context = LocalContext.current
    val settingsDataStore =
        MeeAndroidSettingsDataStore(context)
    val initialFlowDone by settingsDataStore.getInitialFlowDoneSetting()
        .collectAsState(
            initial = null
        )
    val hadConnectionsBefore by settingsDataStore.getHadConnectionsBeforeSetting()
        .collectAsState(
            initial = null
        )

    val coroutineScope = rememberCoroutineScope()

    MeeIdentityAgentTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            var loginSuccess by rememberSaveable { mutableStateOf(false) }

            val ctx = context as FragmentActivity

            OnLifecycleEvent { owner, event ->
                Log.d("Lifecycle Event: ", event.toString())
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        if (!keyguard.isDeviceSecure) {
                            coroutineScope.launch {
                                settingsDataStore.saveInitialFlowDoneSetting(flag = false)
                            }
                        }
                    }

                    Lifecycle.Event.ON_STOP -> {
                        loginSuccess = false

                    }
                    else -> {}
                }
            }

            if (hadConnectionsBefore != null) {
                if (hadConnectionsBefore == false) {
                    ReferrerClient(ctx).getReferrerUrl {
                        coroutineScope.launch {
                            settingsDataStore.saveReferrerUrlSetting(it)
                        }
                    }
                }
            }

            if (initialFlowDone != null) {
                if (loginSuccess || initialFlowDone == false
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .zIndex(1f)
                        ) {
                            MeeNavGraph(
                                initialFlowDone = initialFlowDone == true,
                                hadConnectionsBefore = hadConnectionsBefore == true
                            )
                        }

                    }
                } else {
                    MeeWhiteScreen()
                    BiometryHandler(
                        activityContext = ctx,
                        onSuccessfulAuth = { loginSuccess = true }
                    )
                }
            }
        }
    }
}