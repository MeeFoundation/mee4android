package foundation.mee.android_client.views.consent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentEntryType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.views.components.DatePicker
import foundation.mee.android_client.views.components.clickableWithoutRipple

@Composable
fun RowScope.ConsentSimpleEntryInput(
    entry: ConsentRequestClaim,
    updateValue: (String, String) -> Unit,
) {

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

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
            if (!entry.isOn && !entry.isRequired) ChevronRightIconColor
            else {
                if (entry.isOpen)
                    PartnerEntryOnBackgroundColor
                else MeePrimary
            },
            textAlign = TextAlign.Left
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 11.dp)
            .weight(1f),
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
    if (entry.type == ConsentEntryType.date) {
        Row(modifier = Modifier.padding(end = 16.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_calendar,
                ),
                contentDescription = null,
                tint = DefaultGray400,
                modifier = Modifier
                    .width(18.dp)
                    .clickableWithoutRipple {
                        showDatePicker = true
                    }
            )
        }
    }

    if (showDatePicker) {
        DatePicker(value = entry.value,
            onValueChange = {
                updateValue(entry.id, it)
                showDatePicker = false
            }, onDismiss = {
                showDatePicker = false
            }
        )
    }
}