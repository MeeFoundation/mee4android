package foundation.mee.android_client.views.consent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.models.CreditCard
import foundation.mee.android_client.ui.theme.*
import foundation.mee.android_client.utils.getConsentEntryIconByType

@Composable
private fun Placeholder(text: String) {
    Text(
        text = text,
        fontFamily = publicSansFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight(400),
        color = DefaultGray400,
        textAlign = TextAlign.Left,
    )
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ConsentCardEntryInput(
    entry: ConsentRequestClaim,
    isReadOnly: Boolean = false,
    updateValue: (String, String) -> Unit,
) {
    val textStyle = TextStyle(
        fontFamily = publicSansFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        textAlign = TextAlign.Left,
        color = TextActive
    )
    val emptyCreditCardValue = CreditCard(number = null, cvc = null, expirationDate = null)
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<CreditCard> = moshi.adapter()

    var creditCardEntryValues by remember {
        mutableStateOf(emptyCreditCardValue.copy())
    }

    LaunchedEffect(key1 = entry) {
        creditCardEntryValues = entry.getCardTypeFields() ?: emptyCreditCardValue.copy()
    }

    fun updateValue() {
        val updatedValueStringified = jsonAdapter.toJson(creditCardEntryValues)
        updateValue(entry.id, updatedValueStringified)
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = getConsentEntryIconByType(entry.type),
                ),
                contentDescription = null,
                tint = if (isReadOnly) TextActive.copy(0.45f) else Color.Black,
                modifier = Modifier
                    .height(16.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 11.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            BasicTextField(
                readOnly = !entry.isOn || isReadOnly,
                value = creditCardEntryValues.number ?: "",
                onValueChange = {
                    creditCardEntryValues.number = it
                    updateValue()
                },
                textStyle = textStyle,
                decorationBox = { innerTextField ->
                    if (creditCardEntryValues.number.isNullOrEmpty()) {
                        Placeholder(stringResource(R.string.credit_card_number_placeholder))
                    }
                    innerTextField()
                }
            )
            Row {
                Column(Modifier.weight(1f)) {
                    BasicTextField(
                        readOnly = !entry.isOn || isReadOnly,
                        value = creditCardEntryValues.cvc ?: "",
                        onValueChange = {
                            creditCardEntryValues.cvc = it
                            updateValue()
                        },
                        textStyle = textStyle,
                        decorationBox = { innerTextField ->
                            if (creditCardEntryValues.cvc.isNullOrEmpty()) {
                                Placeholder(stringResource(R.string.credit_card_cvc_placeholder))
                            }
                            innerTextField()
                        }
                    )
                }

                Column(Modifier.weight(1f)) {
                    BasicTextField(
                        readOnly = !entry.isOn || isReadOnly,
                        value = creditCardEntryValues.expirationDate ?: "",
                        onValueChange = {
                            creditCardEntryValues.expirationDate = it
                            updateValue()
                        },
                        textStyle = textStyle,
                        decorationBox = { innerTextField ->
                            if (creditCardEntryValues.expirationDate.isNullOrEmpty()) {
                                Placeholder(stringResource(R.string.credit_card_valid_thru_placeholder))
                            }
                            innerTextField()
                        }
                    )
                }
            }

        }
    }


}