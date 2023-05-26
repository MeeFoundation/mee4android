package foundation.mee.android_client.views.initial_flow

import androidx.biometric.BiometricManager
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.controller.biometry.BiometryHandler
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.views.MeeWhiteScreen
import foundation.mee.android_client.views.welcome_pages.WelcomeScreen
import kotlinx.coroutines.launch

enum class InitialFlowSteps {
    Initial,
    Validate,
    AllSet,
    Animation,
    Recover,
}

@Composable
fun InitialFlow(
    viewModel: NavViewModel = hiltViewModel(),
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var currentStep by remember {
        mutableStateOf(InitialFlowSteps.Initial)
    }
    val navigator = viewModel.navigator
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
                            icon = painterResource(R.drawable.mee_guy_icon),
                            iconSize = 60.dp,
                            title = "Set up your Privacy Agent",
                            message = "To store your data, Mee contains a secure data vault, which is protected by Biometry. Please set up Biometry.",
                            onNext = { currentStep = InitialFlowSteps.Validate }
                        )
                }
            }



        }
        InitialFlowSteps.Validate -> {
            fun onNext() {
                currentStep = InitialFlowSteps.AllSet
            }
            val biometricManager = BiometricManager.from(context)
            MeeWhiteScreen()
            if (biometricManager.canAuthenticate(
                    BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                BiometryHandler(
                    activityContext = context as FragmentActivity,
                    onSuccessfulAuth = { onNext() })
            } else {
                onNext()
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
                        icon = painterResource(R.drawable.all_set),
                        iconSize = 60.dp,
                        title = "All Set!",
                        message = "Please use Biometry next time you sign-in.",
                        onNext = { currentStep = InitialFlowSteps.Animation }
                    )
                }
            }

        }
        InitialFlowSteps.Animation -> {
            InitialFlowLoadingScreen{ currentStep = InitialFlowSteps.Recover }

        }
        InitialFlowSteps.Recover -> {
            WelcomeScreen(
                screenImages = arrayOf(
                    R.drawable.welcome_screen1,
                    R.drawable.mee_first_run2,
                )) {
                coroutineScope.launch {
                    settingsDataStore.saveInitialFlowDoneSetting(true)
                    navigator.navController.popBackStack()
                }
            }

        }
    }
}