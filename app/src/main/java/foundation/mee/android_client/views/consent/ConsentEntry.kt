package foundation.mee.android_client.views.consent

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentEntryType
import foundation.mee.android_client.utils.getConsentEntryIconByType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.ui.components.Toggle
import foundation.mee.android_client.ui.components.clickableWithoutRipple

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

    val onChange = {
        updateIsOn(entry.id, !entry.isOn)
        updateIsOpen(entry.id, !entry.isOn)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = getConsentEntryIconByType(entry.type),
                ),
                contentDescription = null,
                tint = if (entry.isOn || entry.isRequired || isReadOnly) MeeGreenPrimaryColor else ChevronRightIconColor,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
                    .clickableWithoutRipple {
                        if ((entry.isRequired || entry.isOn) && !isReadOnly) {
                            updateIsOpen(entry.id, !entry.isOpen)
                        }
                    }
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 13.dp)
                .weight(1f)
                .border(
                    1.dp,
                    if (entry.isOpen && (entry.isRequired || entry.isOn)) {
                        if (entry.isIncorrect()) {
                            DefaultRedLight
                        } else {
                            MeeGreenPrimaryColor
                        }
                    } else {
                        Color.Transparent
                    },
                    RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isReadOnly && (entry.isRequired && entry.isOpen) || (!entry.isRequired && entry.isOn && entry.isOpen)) {
                    if (entry.type == ConsentEntryType.card) {
                        ConsentCardEntryInput(entry = entry, updateValue = updateValue)
                    } else {
                        ConsentSimpleEntryInput(entry = entry, updateValue = updateValue)
                    }
                } else {
                    Text(
                        text = entry.getFieldName(),
                        color = if (entry.isRequired || entry.isOn || isReadOnly) MeeBrand else ChevronRightIconColor,
                        fontFamily = publicSansFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400),
                        modifier = Modifier
                            .clickableWithoutRipple {
                                if (entry.isRequired || entry.isOn) {
                                    updateIsOpen(entry.id, !entry.isOpen)
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }

            }
        }
        if (!isReadOnly) {
            if (!entry.isRequired) {
                Toggle(entry.isOn, onChange, modifier = Modifier.height(16.dp))
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_chevron_right,
                    ),
                    contentDescription = null,
                    tint = DefaultGray400,
                    modifier = Modifier
                        .padding(start = 27.dp)
                        .width(18.dp)
                        .clickableWithoutRipple {
                            onDurationPopupShow()
                        }
                )

            }
        }

    }
}
