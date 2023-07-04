package foundation.mee.android_client.views.consent

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.models.settings.MeeAndroidSettingsDataStore
import foundation.mee.android_client.navigation.MeeDestinations
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.ui.components.DeclineButton
import foundation.mee.android_client.ui.components.Expander
import foundation.mee.android_client.ui.components.NoRippleInteractionSource
import foundation.mee.android_client.ui.components.PrimaryButton
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.utils.CERTIFIED_URL_STRING
import foundation.mee.android_client.utils.getURLFromString
import foundation.mee.android_client.utils.linkToWebpage
import foundation.mee.android_client.utils.showConsentToast
import kotlinx.coroutines.launch
import uniffi.mee_agent.RpAuthResponseWrapper

@Composable
fun ConsentPageNew(
    consentViewModel: ConsentViewModel,
    viewModel: NavViewModel = hiltViewModel(),
    onAccept: (ConsentRequest) -> RpAuthResponseWrapper?
) {
    val data by consentViewModel.uiState.collectAsState()
    val navigator = viewModel.navigator

    val context = LocalContext.current
    val activity = context as Activity

    val hasOptionalFields = data.claims.any { x -> !x.isRequired }

    val optionalClaims = data.claims.filter { x -> !x.isRequired }
    val requiredClaims = data.claims.filter { x -> x.isRequired }

    fun navigateToMainScreen() {
        navigator.navigate(MeeDestinations.CONNECTIONS.route)
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 3f)

    val settingsDataStore = MeeAndroidSettingsDataStore(context = LocalContext.current)
    val hadConnectionsBefore by settingsDataStore.getHadConnectionsBeforeSetting()
        .collectAsState(
            initial = false
        )
    val coroutineScope = rememberCoroutineScope()

    val state = rememberConsentPageNewState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        top = 30.dp,
                        bottom = 24.dp
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.mee_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(48.dp)
                    )
                    Canvas(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        drawLine(
                            color = MeeGreenPrimaryColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect,
                            strokeWidth = 2f
                        )
                    }
                    IconButton(
                        onClick = { state.showCertified = !state.showCertified },
                        interactionSource = NoRippleInteractionSource()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.mee_certified_logo),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Canvas(
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        drawLine(
                            color = MeeGreenPrimaryColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect,
                            strokeWidth = 2f
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = data.clientMetadata.logoUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(
                    text = data.clientMetadata.name,
                    fontFamily = publicSansFamily,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    color = PartnerEntryOnBackgroundColor,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = getURLFromString(data.id)?.host ?: data.id,
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = MeeGreenPrimaryColor,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "would like access to your information",
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = ChevronRightIconColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 36.dp)
                        .padding(horizontal = 10.dp)
                )

                Expander(
                    title = "Required",
                    modifier = Modifier.padding(bottom = 16.dp),
                    isExpanded = state.isRequiredSectionOpened,
                    onChangeExpanded = {
                        state.isRequiredSectionOpened = !state.isRequiredSectionOpened
                    }
                ) {
                    LazyColumn(
                        state = state.requiredListState,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        items(requiredClaims) { x ->
                            ConsentEntry(x,
                                updateValue = { id, value ->
                                    consentViewModel.updateValue(
                                        id,
                                        value
                                    )
                                }, updateIsOn = { id, isOn ->
                                    consentViewModel.updateIsOn(
                                        id,
                                        isOn
                                    )
                                }, updateIsOpen = { id, isOpen ->
                                    consentViewModel.updateIsOpen(
                                        id,
                                        isOpen
                                    )
                                }) { state.durationPopupId = x.id }
                        }
                    }
                }

                Divider(
                    color = DefaultGray200,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (hasOptionalFields) {
                    Expander(
                        title = "Optional",
                        isExpanded = state.isOptionalSectionOpened,
                        onChangeExpanded = {
                            state.isOptionalSectionOpened = !state.isOptionalSectionOpened
                        }
                    ) {
                        LazyColumn(
                            state = state.optionalListState,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            items(optionalClaims) {
                                ConsentEntry(it,
                                    updateValue = { id, value ->
                                        consentViewModel.updateValue(
                                            id,
                                            value
                                        )
                                    }, updateIsOn = { id, isOn ->
                                        consentViewModel.updateIsOn(
                                            id,
                                            isOn
                                        )
                                    }, updateIsOpen = { id, isOpen ->
                                        consentViewModel.updateIsOpen(
                                            id,
                                            isOpen
                                        )
                                    }) {
                                    state.durationPopupId = it.id
                                }
                            }
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = PartnerEntryBackgroundColor)
                    .sizeIn(maxHeight = 159.dp)
            ) {
                Row(modifier = Modifier.padding(top = 14.dp)) {
                    DeclineButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        "Decline",
                    ) {
                        val uri = state.onDeclineBuildUri(data.redirectUri)
                        if (uri != null) {
                            linkToWebpage(context, uri)
                        } else {
                            showConsentToast(context, "Unknown Error")
                        }
                        activity.finishAffinity()
                    }
                }
                Row(modifier = Modifier.padding(bottom = 30.dp)) {
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),
                        title = "Approve and Continue"
                    ) {
                        val incorrectClaim = state.findIncorrectClaim(data.claims)

                        if (incorrectClaim != null) {
                            val index = state.findIndexById(
                                if (incorrectClaim.isRequired) requiredClaims else optionalClaims,
                                incorrectClaim.id
                            )

                            state.scrollToPosition(index, incorrectClaim.isRequired)

                            consentViewModel.updateIsOpen(
                                incorrectClaim.id,
                                true
                            )
                        } else {
                            val response = onAccept(data)
                            if (response != null) {
                                try {
                                    onNext(response, data.redirectUri, context, data.nonce, data.isCrossDeviceFlow)
                                    if (!hadConnectionsBefore) {
                                        coroutineScope.launch {
                                            settingsDataStore.saveHadConnectionsBeforeSetting(flag = true)
                                        }
                                    }
                                    if (data.isCrossDeviceFlow) navigateToMainScreen() else activity.finishAffinity()
                                    
                                } catch (e: Exception) {
                                    showConsentToast(
                                        context,
                                        "Unknown error"
                                    )
                                }
                            } else {
                                showConsentToast(context, "Connection failed. Please try again.")
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.shouldShowDurationPopup) {
        Dialog(
            onDismissRequest = {},
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
            dialogWindowProvider.window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LocalView.current.layoutParams.height
            )

            Surface(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                if (state.shouldShowDurationPopup) {
                    ConsentDuration(
                        consentEntries = data.claims,
                        id = state.durationPopupId!!
                    ) {
                        state.durationPopupId = null
                    }
                }
            }
        }
    }

    if (state.showCertified) {
        WebViewComposable(CERTIFIED_URL_STRING) {
            state.showCertified = !state.showCertified
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable(url: String, onClose: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 16.dp)
        ) {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            })

            PrimaryButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(51.dp),
                title = "Close"
            ) { onClose() }
        }
    }
}
