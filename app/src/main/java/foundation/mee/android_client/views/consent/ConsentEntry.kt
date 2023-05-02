package foundation.mee.android_client.views.consent

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.helpers.getConsentEntryIconByType
import foundation.mee.android_client.models.ConsentEntryType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.views.components.DatePicker
import foundation.mee.android_client.views.components.Toggle


@Composable
fun ConsentEntry(
    entry: ConsentRequestClaim,
    updateValue: (String, String) -> Unit,
    updateIsOn: (String, Boolean) -> Unit,
    updateIsOpen: (String, Boolean) -> Unit,
    onDurationPopupShow: () -> Unit
) {

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val onChange = {
        updateIsOn(entry.id, !entry.isOn)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { updateIsOpen(entry.id, !entry.isOpen) }) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = getConsentEntryIconByType(entry.type),
                    ),
                    contentDescription = "consentType",
                    tint = if (!entry.isOn) ChevronRightIconColor else MeePrimary
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 13.dp)
                .border(
                    1.dp,
                    if (entry.isOpen && entry.isOn) {
                        if (entry.isIncorrect()) {
                            DefaultRedLight
                        } else {
                            MeePrimary
                        }
                    } else {
                        Color.Transparent
                    },
                    RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if ((entry.isRequired && entry.isOpen) || (!entry.isRequired && entry.isOn)) {
                    BasicTextField(
                        value = entry.value ?: "",
                        onValueChange = {
                            updateValue(entry.id, it)
                        },
                        textStyle = TextStyle(
                            fontFamily = publicSansFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400),
                            color =
                            if (!entry.isOn) ChevronRightIconColor
                            else {
                                if (entry.isOpen)
                                    PartnerEntryOnBackgroundColor
                                else MeePrimary
                            },
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 11.dp)
                            .focusable(),
                        decorationBox = { innerTextField ->
                            if (entry.value.isNullOrEmpty()) {
                                Text(
                                    text = entry.name,
                                    fontFamily = publicSansFamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(400),
                                    color =
                                    if (!entry.isOn)
                                        ChevronRightIconColor
                                    else {
                                        if (entry.isOpen)
                                            DefaultGray400
                                        else MeePrimary
                                    },
                                    textAlign = TextAlign.Left
                                )
                            }
                            innerTextField()
                        }
                    )
                } else {
                    TextButton(onClick = {
                        updateIsOpen(entry.id, !entry.isOpen)
                    }) {
                        Text(
                            text = entry.getFieldName(),
                            color = if (entry.isRequired) MeeBrand else ChevronRightIconColor,
                            fontFamily = publicSansFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                }

                if (entry.type == ConsentEntryType.date) {
                    IconButton(
                        onClick = {
                            showDatePicker = true
                        },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = R.drawable.ic_calendar,
                            ),
                            contentDescription = null,
                            tint = DefaultGray400,
                            modifier = Modifier.width(14.dp)
                        )
                    }
                }

            }
        }
        if (!entry.isRequired) {
            Toggle(!entry.isOn, onChange)
        } else {
            IconButton(
                onClick = {
                    onDurationPopupShow()
                },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_chevron_right,
                    ),
                    contentDescription = "consentChevronRight",
                    tint = DefaultGray400,
                    modifier = Modifier.width(14.dp)
                )
            }
        }

        // TODO валидация даты
        // TODO datepicker question more than n years old
        if (showDatePicker) {
            DatePicker(value = entry.value, show = showDatePicker,
                onValueChange = {
                    updateValue(entry.id, it)
                    showDatePicker = false
                }, onDismiss = {
                    showDatePicker = false
                }
            )
        }
    }
}


/*
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConsentEntry() {
    MeeIdentityAgentTheme {
        val consentRequestClaim = consentRequestMock.claims[0]
        ConsentEntry(consentRequestClaim, isReadOnly = false) {}
    }
}

*/
