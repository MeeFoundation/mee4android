package foundation.mee.android_client.views.consent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.utils.getConsentEntryIconByType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.*
import uniffi.mee_agent.RetentionDuration

@Composable
fun ConsentDuration(consentEntries: List<ConsentRequestClaim>, id: String, onComplete: () -> Unit) {

    val consentEntry = consentEntries.first { it.id == id }

    var storageDuration by rememberSaveable {
        mutableStateOf(
            when (consentEntry.retentionDuration) {
                RetentionDuration.WHILE_USING_APP -> RetentionDuration.WHILE_USING_APP
                else -> RetentionDuration.UNTIL_CONNECTION_DELETION
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DurationPopupBackground,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Metadata",
                fontFamily = publicSansFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Black
            )
            Divider(
                color = LabelLightSecondary,
                thickness = 0.5.dp,
                modifier = Modifier.alpha(0.5f)
            )
            Column(Modifier.padding(top = 16.dp, bottom = 34.dp, start = 16.dp, end = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .padding(13.dp)
                        .fillMaxWidth()
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "STORAGE DURATION",
                        fontFamily = publicSansFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = LabelLightSecondary
                    )
                }

                Column(
                    modifier = Modifier.background(Color.White, RoundedCornerShape(14.dp))
                ) {
                    ConsentDurationOptions.forEach { durationElement ->
                        ConsentDurationEntry(
                            text = durationElement.name,
                            description = durationElement.description,
                            selected = durationElement.value == storageDuration,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 9.dp
                            )
                        ) {
                            storageDuration = durationElement.value
                        }
                        if (durationElement != ConsentDurationOptions.last()) {
                            Divider(
                                color = LabelLightSecondary,
                                thickness = 0.5.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .alpha(0.5f)
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 90.dp, end = 28.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = MeePrimary,
                    fontFamily = publicSansFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .clickableWithoutRipple { onComplete() }
                )
                Text(
                    text = "Save",
                    color = MeePrimary,
                    fontFamily = publicSansFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier
                        .clickableWithoutRipple {
                            consentEntries.first { it.id == id }.retentionDuration = storageDuration
                            onComplete()
                        }
                )
            }
        }
    }
}