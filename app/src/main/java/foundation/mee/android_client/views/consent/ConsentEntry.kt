package foundation.mee.android_client.views.consent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentEntryType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.manageConnectionDataMock
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.ui.components.Toggle
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.views.manage.ConsentEntriesType.SiopClaims

@Composable
fun ConsentEntry(
    entry: ConsentRequestClaim,
    modifier: Modifier = Modifier,
    isReadOnly: Boolean = false,
    updateValue: (String, String) -> Unit = { _, _ -> },
    updateIsOn: (String, Boolean) -> Unit = { _, _ -> },
    updateIsOpen: (String, Boolean) -> Unit = { _, _ -> },
    onDurationPopupShow: () -> Unit = {},
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    val onChange = {
        updateIsOn(entry.id, !entry.isOn)
//        updateIsOpen(entry.id, !entry.isOn)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            if (entry.isOpen) Row(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .zIndex(0.5f)
            ) {
                Text(
                    text = entry.name,
                    fontFamily = publicSansFamily,
                    color = if (isReadOnly) TextActive else if (isFocused && entry.isOn) MeeGreenPrimaryColor else DarkText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier
                        .background(LightBackground)
                        .padding(horizontal = 4.dp)
                )
            }



            Box(
                modifier = Modifier
                    .padding(start = 0.dp, top = 8.dp)
                    .onFocusChanged { isFocused = it.isFocused }
                    .clip(
                        RoundedCornerShape(
                            topEnd = 4.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                            bottomStart = 4.dp
                        )
                    )
                    .background(if (!entry.isOn || isReadOnly) GrayText else Color.Transparent)
                    .border(
                        if (isFocused && entry.isOn && !isReadOnly) 2.dp else 1.dp,
                        if (entry.isOn && !isReadOnly) {
                            if (entry.isIncorrect()) {
                                DefaultRedLight
                            } else if (isFocused) {
                                MeeGreenPrimaryColor
                            } else {
                                InactiveBorder
                            }
                        } else {
                            GrayText
                        },
                        RoundedCornerShape(4.dp)
                    )

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (entry.type == ConsentEntryType.card) {
                        ConsentCardEntryInput(
                            entry = entry,
                            isReadOnly = isReadOnly,
                            updateValue = updateValue
                        )
                    } else {
                        ConsentSimpleEntryInput(
                            entry = entry,
                            isReadOnly = isReadOnly,
                            updateValue = updateValue
                        )
                    }

                }
                if (isReadOnly || !entry.isOn) Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.White.copy(alpha = 0.3f))
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = InactiveCover.copy(alpha = 0.5f)
                            )
                        )
                )
            }
        }
        if (entry.isRequired) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.storage_duration,
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(48.dp)
                    .clickableWithoutRipple {
                        onDurationPopupShow()
                    }
            )
        } else {
            if (!isReadOnly) {
                Toggle(entry.isOn, onChange, modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun ConsentEntryPreview() {
    val entry = manageConnectionDataMock.connectorToEntries[0].consentEntriesType as SiopClaims
    ConsentEntry(
        entry = entry.value.first(),
        isReadOnly = false
    )
}
