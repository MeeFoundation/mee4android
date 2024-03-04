package foundation.mee.android_client.views.consent

import android.view.Gravity
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.utils.getConsentEntryIconByType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import uniffi.mee_agent.RetentionDuration

@Composable
fun ConsentDuration(
    consentEntry: ConsentRequestClaim,
    isReadOnly: Boolean = false,
    onSave: (RetentionDuration) -> Unit,
    onClose: () -> Unit
) {

    var storageDuration by rememberSaveable {
        mutableStateOf(
            when (consentEntry.retentionDuration) {
                RetentionDuration.WHILE_USING_APP -> RetentionDuration.WHILE_USING_APP
                else -> RetentionDuration.UNTIL_CONNECTION_DELETION
            }
        )
    }
    Dialog(
        onDismissRequest = { }
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        with(dialogWindowProvider.window) {
            setGravity(Gravity.BOTTOM)
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LocalView.current.layoutParams.height
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .zIndex(1f)
                .padding(horizontal = 50.dp)
                .fillMaxHeight(),
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topEnd = 28.dp,
                            topStart = 28.dp,
                            bottomEnd = 28.dp,
                            bottomStart = 28.dp
                        )
                    ),
                color = DurationPopupBackground,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(24.dp)

                ) {
                    Text(
                        text = stringResource(R.string.consent_storage_duration_text),
                        fontFamily = publicSansFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        color = Color.Black
                    )
                    Column(

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = getConsentEntryIconByType(consentEntry.type),
                                ), contentDescription = null,
                                tint = ChevronRightIconColor,
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                            )
                            Text(
                                text = consentEntry.name,
                                modifier = Modifier.padding(start = 11.dp),
                                fontFamily = publicSansFamily,
                                fontSize = 17.sp,
                                fontWeight = FontWeight(700),
                                color = Color.Black
                            )
                        }
                        Divider(
                            color = LabelLightSecondary,
                            thickness = 0.5.dp,
                            modifier = Modifier
                                .alpha(0.5f)
                        )
                        Column(
                            modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
                        ) {
                            ConsentDurationOptions.forEach { durationElement ->
                                ConsentDurationEntry(
                                    text = stringResource(durationElement.name),
                                    description = stringResource(durationElement.description),
                                    selected = durationElement.value == storageDuration,
                                    isDisabled = isReadOnly,
                                ) {
                                    storageDuration = durationElement.value
                                }
                                Divider(
                                    color = LabelLightSecondary,
                                    thickness = 0.5.dp,
                                    modifier = Modifier
                                        .alpha(0.5f)
                                )
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (isReadOnly) {
                            Text(
                                text = stringResource(R.string.close_button_text),
                                color = MeeGreenPrimaryColor,
                                fontFamily = publicSansFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                modifier = Modifier
                                    .clickableWithoutRipple { onClose() }
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.negative_button_text),
                                color = MeeGreenPrimaryColor,
                                fontFamily = publicSansFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                modifier = Modifier
                                    .padding(end = 32.dp)
                                    .clickableWithoutRipple { onClose() }
                            )
                            Text(
                                text = stringResource(R.string.save_button_text),
                                color = MeeGreenPrimaryColor,
                                fontFamily = publicSansFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                modifier = Modifier
                                    .clickableWithoutRipple {
                                        onSave(storageDuration)
                                        onClose()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}