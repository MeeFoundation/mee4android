package foundation.mee.android_client.views.consent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.*

fun getExternalConsentEntryIconByName(name: String): Int {
    return when (name) {
        "familyName", "givenName", "name" -> R.drawable.ic_person
        "email" -> R.drawable.ic_letter
        else -> R.drawable.ic_key
    }
}

@Composable
fun ExternalConsentEntry(
    name: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = getExternalConsentEntryIconByName(name),
                ),
                tint = MeeBrand,
                contentDescription = null,
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 13.dp)
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = MeeBrand,
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}