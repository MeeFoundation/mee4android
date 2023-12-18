package foundation.mee.android_client.views.consent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentEntryType
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.ui.components.DatePicker
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.utils.getConsentEntryIconByType

@Composable
fun RowScope.ConsentSimpleEntryInput(
    entry: ConsentRequestClaim,
    isReadOnly: Boolean = false,
    updateValue: (String, String) -> Unit
) {

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    BasicTextField(
        readOnly = !entry.isOn || isReadOnly,
        value = entry.value ?: "",
        onValueChange = {
            updateValue(entry.id, it)
        },
        textStyle = TextStyle(
            fontFamily = publicSansFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            color = TextActive,
            textAlign = TextAlign.Left
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .weight(1f),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Column(
                    modifier = Modifier.padding(end = 16.dp),
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = getConsentEntryIconByType(entry.type),
                        ),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .height(16.dp)
                    )
                }
                innerTextField()
            }

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