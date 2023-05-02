package foundation.mee.android_client.views.consent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.helpers.getConsentEntryIconByType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.consentRequestMock
import foundation.mee.android_client.ui.theme.*
import uniffi.mee_agent.RetentionDuration

// TODO мб вынести
data class ConsentDurationOption(
    val id: String, val name: String, val description: String, val value: RetentionDuration
)

//  TODO id странное
//  TODO убрать материал дизайн
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
        modifier = Modifier
            .sizeIn(minHeight = 64.dp)
            .fillMaxWidth(),
        color = DurationPopupBackground,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onComplete() }) {
                    Text(
                        text = "Cancel", color = Color.Blue
                    )
                }
                Text(
                    text = "Metadata",
                    fontFamily = publicSansFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700),
                )
                TextButton(onClick = {
                    consentEntries.first { it.id == id }.retentionDuration = storageDuration
                    onComplete()
                }) {
                    Text(
                        text = "Save", color = Color.Blue
                    )
                }
            }
            Divider(
                color = DefaultGray200,
                thickness = 1.dp,
            )
            Column(Modifier.padding(top = 16.dp, bottom = 112.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = getConsentEntryIconByType(consentEntry.type),
                        ), contentDescription = null, tint = ChevronRightIconColor
                    )
                    Text(
                        text = consentEntry.name,
                        modifier = Modifier.padding(start = 11.dp),
                        fontFamily = publicSansFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black
                    )
                    // TODO providedBy
                }

                Spacer(modifier = Modifier.size(34.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Storage duration",
                        modifier = Modifier.padding(start = 11.dp),
                        fontFamily = publicSansFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = ChevronRightIconColor
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Column(
                    modifier = Modifier.background(Color.White, RoundedCornerShape(14.dp))
                ) {
                    ConsentDurationOptions.forEach { durationElement ->
                        ConsentDurationEntry(
                            text = durationElement.name,
                            description = durationElement.description,
                            durationElement.value == storageDuration
                        ) {
                            storageDuration = durationElement.value
                        }
                        if (durationElement != ConsentDurationOptions.last()) {
                            Divider(
                                color = ChevronRightIconColor, thickness = 0.5.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ConsentDurationPreview() {
    MeeIdentityAgentTheme {
        ConsentDuration(consentRequestMock.claims, "First Name") {}
    }
}