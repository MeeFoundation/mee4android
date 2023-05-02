package foundation.mee.android_client.views.consent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.getURLFromString
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.ui.theme.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import uniffi.mee_agent.RpAuthResponseWrapper
import androidx.compose.foundation.lazy.items
import kotlinx.coroutines.launch


// Scrollable index
// TODO experimantal
// TODO toast
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConsentPageNew(
    consentViewModel: ConsentViewModel = viewModel(),
    onAccept: (ConsentRequest) -> RpAuthResponseWrapper?
) {

    // TODO
    val cns by consentViewModel.uiState.collectAsState()
    val data = cns.consent

    val context = LocalContext.current

    val hasOptionalFields = data.claims.any { x -> !x.isRequired }
    val optionalClaims = data.claims.filter { x -> !x.isRequired }
    val requiredClaims = data.claims.filter { x -> x.isRequired }


    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val state = rememberConsentPageNewState()

    val toast = { ctx: Context, message: String ->
        Toast.makeText(
            ctx,
            message,
            Toast.LENGTH_SHORT
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 90.dp),
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = "https://mee.foundation/favicon.png"), //TODO
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .clip(shape = CircleShape),
                    )

                    Canvas(
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                    ) {
                        drawLine(
                            color = MeePrimary,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.mee_certified_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .clip(shape = CircleShape),
                    )
                    Canvas(
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                    ) {
                        drawLine(
                            color = MeePrimary,
                            start = Offset(1f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = data.clientMetadata.logoUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 48.dp, height = 48.dp)
                            .clip(shape = CircleShape),
                    )
                }
                Text(
                    text = data.clientMetadata.name,
                    fontFamily = publicSansFamily,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(700),
                    color = PartnerEntryOnBackgroundColor,
                    textAlign = TextAlign.Center,
//                modifier = Modifier.paddingFromBaseline(bottom = 10.dp),
                )

                Text(
                    text = getURLFromString(data.id)?.host ?: data.id,
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = MeePrimary,
                    textAlign = TextAlign.Center,
//                modifier = Modifier.paddingFromBaseline(bottom = 10.dp)
                )
                Text(
                    text = "would like access to your information",
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                    color = ChevronRightIconColor,
                    textAlign = TextAlign.Center,
                )

                Expander(
                    title = "Required",
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    isExpanded = state.isRequiredSectionOpened, // TODO
                    onChangeExpanded = {
                        state.isRequiredSectionOpened = !state.isRequiredSectionOpened
                    }
                ) {
                    LazyColumn(state = state.requiredListState) {
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
                )

                if (hasOptionalFields) {
                    Expander(
                        title = "Optional",
                        modifier = Modifier.padding(top = 16.dp, bottom = 40.dp),
                        isExpanded = state.isOptionalSectionOpened,
                        onChangeExpanded = {
                            state.isOptionalSectionOpened = !state.isOptionalSectionOpened
                        }
                    ) {

                        LazyColumn(state = state.optionalListState) {
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
                    .padding(horizontal = 16.dp)
                    .background(color = PartnerEntryBackgroundColor)
            ) {
                TextButton(
                    onClick = {
                        try {
                            // TODO onDecline
                            linkToWebpage(context, Uri.parse(data.redirectUri))
                        } catch (e: java.lang.Exception) {
                            Log.e("consentPage", "Error while parsing uri")
                            toast(context, "Unknown Error").show()

                        }
                    },
                    colors = ButtonDefaults.buttonColors(PartnerEntryBackgroundColor)
                ) {
                    Text(
                        text = "Decline",
                        color = MeePrimary,
                        fontFamily = publicSansFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(
                    onClick = {
                        val incorrectId = state.incorrectClaimId(data.claims)
                        if (incorrectId != null) { // TODO
                            val required = data.claims.find { it.id == incorrectId }!!.isRequired
                            consentViewModel.updateIsOpen(
                                incorrectId,
                                true
                            )
                            val index = state.findIndexById(
                                if (required) requiredClaims else optionalClaims,
                                incorrectId
                            )
                            state.scrollToPosition(index, required)
                        } else {
                            val response = onAccept(data)
                            if (response != null) {
                                onNext(response, data.redirectUri, context)
                                Log.d("response", response.toString())
                            } else {
                                toast(context, "Connection failed. Please try again.").show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(MeePrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Approve and Continue",
                        color = Color.White,
                        fontFamily = publicSansFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    if (state.shouldShowDurationPopup) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {

            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

            Surface(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Box(modifier = Modifier.width(IntrinsicSize.Max))
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
}

fun linkToWebpage(context: Context, uri: Uri) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = uri
    ContextCompat.startActivity(context, openURL, null)
}

/*
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConsentPage() {
    MeeIdentityAgentTheme {
        ConsentPageNew(
            consent = consentRequestMock,
            authorizeRequest = { null }
        )
    }
}

*/

