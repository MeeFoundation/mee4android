package foundation.mee.android_client.views.consent

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import foundation.mee.android_client.models.ConsentRequestClaim
import foundation.mee.android_client.ui.theme.*
import java.lang.Exception

data class CreditCard(
    var number: String? = null,
    var exp: String? = null,
    var cvc: String? = null,
)

enum class CreditCardFieldType {
    number,
    exp,
    cvc
}

val emptyCreditCardValue = CreditCard(number = null, cvc = null, exp = null)

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun RowScope.ConsentCardEntryInput(
    entry: ConsentRequestClaim,
    updateValue: (String, String) -> Unit,
) {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<CreditCard> = moshi.adapter()

    var creditCardEntryValues by remember {
        mutableStateOf(emptyCreditCardValue)
    }

    LaunchedEffect(key1 = entry) {
        Log.d("value: ", entry.value ?: "")
        try {
            val parsedValue = entry.value?.let { jsonAdapter.fromJson(it) }
            if (parsedValue != null) {
                creditCardEntryValues = parsedValue
            }
        } catch (e: IllegalArgumentException) {

        }

    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 11.dp)
    ) {
        TextField(
            value = creditCardEntryValues?.number ?: "",
            onValueChange = {
                creditCardEntryValues?.number = it
                val updatedValueStringified = jsonAdapter.toJson(creditCardEntryValues)
                updateValue(entry.id, updatedValueStringified)
            },
            textStyle = TextStyle(
                fontFamily = publicSansFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = if (!entry.isOn && !entry.isRequired) ChevronRightIconColor
                else {
                    if (entry.isOpen) PartnerEntryOnBackgroundColor
                    else MeeGreenPrimaryColor
                },
                textAlign = TextAlign.Left
            ),
        )
        TextField(
            value = creditCardEntryValues?.cvc ?: "",
            onValueChange = {
                creditCardEntryValues?.cvc = it
                val updatedValueStringified = jsonAdapter.toJson(creditCardEntryValues)
                updateValue(entry.id, updatedValueStringified)
            },
            textStyle = TextStyle(
                fontFamily = publicSansFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = if (!entry.isOn && !entry.isRequired) ChevronRightIconColor
                else {
                    if (entry.isOpen) PartnerEntryOnBackgroundColor
                    else MeeGreenPrimaryColor
                },
                textAlign = TextAlign.Left
            ),
        )
        TextField(
            value = creditCardEntryValues?.exp ?: "",
            onValueChange = {
                creditCardEntryValues?.exp = it
                val updatedValueStringified = jsonAdapter.toJson(creditCardEntryValues)
                updateValue(entry.id, updatedValueStringified)
            },
            textStyle = TextStyle(
                fontFamily = publicSansFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                color = if (!entry.isOn && !entry.isRequired) ChevronRightIconColor
                else {
                    if (entry.isOpen) PartnerEntryOnBackgroundColor
                    else MeeGreenPrimaryColor
                },
                textAlign = TextAlign.Left
            ),
        )
    }

}