package foundation.mee.android_client.views.initial_flow

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context.KEYGUARD_SERVICE
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.controller.biometry.BiometryHandler
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.MeeWhiteScreen
import foundation.mee.android_client.views.welcome_pages.WelcomeScreen
import kotlinx.coroutines.launch

enum class InitialFlowSteps {
    Initial,
    Validate,
    AllSet,
    Animation,
    Recover,
    Restrict
}

@Composable
fun InitialFlow(
    navigator: Navigator = hiltViewModel<NavViewModel>().navigator,
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    var currentStep by remember {
        mutableStateOf(InitialFlowSteps.Initial)
    }
    val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    when (currentStep) {
        InitialFlowSteps.Initial -> {
            Box {
                MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxHeight(),
                ) {
                    BottomMessage(
                        icon = R.drawable.mee_guy_icon,
                        iconSize = 60.dp,
                        title = R.string.biometry_initial_step_title,
                        message = R.string.biometry_initial_step_message,
                        onNext = { currentStep = InitialFlowSteps.Validate }
                    )
                }
            }


        }
        InitialFlowSteps.Validate -> {
            MeeWhiteScreen()
            val keyguard = context.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            if (keyguard.isDeviceSecure) {
                BiometryHandler(
                    activityContext = context as FragmentActivity,
                    onSuccessfulAuth = { currentStep = InitialFlowSteps.AllSet }
                )
            } else {
                currentStep = InitialFlowSteps.Restrict
            }
        }
        InitialFlowSteps.AllSet -> {
            Box {
                MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxHeight(),
                ) {
                    BottomMessage(
                        icon = R.drawable.all_set,
                        iconSize = 60.dp,
                        title = R.string.biometry_all_set_step_title,
                        message = R.string.biometry_all_set_step_message,
                        onNext = { currentStep = InitialFlowSteps.Animation }
                    )
                }
            }

        }
        InitialFlowSteps.Animation -> {
            InitialFlowLoadingScreen { currentStep = InitialFlowSteps.Recover }

        }
        InitialFlowSteps.Recover -> {
            WelcomeScreen(
                screenImages = arrayOf(
                    R.drawable.welcome_screen1,
                    R.drawable.mee_first_run2,
                )
            ) {
                coroutineScope.launch {
                    settingsDataStore.saveInitialFlowDoneSetting(true)
                    navigator.navController.popBackStack()
                }
            }
        }
        InitialFlowSteps.Restrict -> {
            Box {
                MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxHeight(),
                ) {
                    RestrictBottomMessage(
                        icon = R.drawable.mee_compatible_sign,
                        iconSize = 60.dp,
                        title = R.string.biometry_restrict_step_title,
                        message = R.string.biometry_restrict_step_message,
                        onNextSecondaryButton = { activity?.finishAffinity() }
                    ) {
                        context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                        currentStep = InitialFlowSteps.Initial
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun InitialFlowPreview() {
    MeeIdentityAgentTheme {
        Box {
            MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxHeight(),
            ) {
                BottomMessage(
                    icon = R.drawable.mee_guy_icon,
                    iconSize = 60.dp,
                    title = R.string.biometry_initial_step_title,
                    message = R.string.biometry_initial_step_message,
                    onNext = {}
                )
            }
        }
    }
}