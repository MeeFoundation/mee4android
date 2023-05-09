package foundation.mee.android_client.views.consent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.LabelLightSecondary
import foundation.mee.android_client.ui.theme.SystemBlueLight

@Composable
fun ConsentDurationEntry(
    text: String,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickableWithoutRipple { onClick() }

    ) {
        Column {
            Text(
                text = text,
                fontFamily = publicSansFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color.Black
            )
            Text(
                text = description,
                fontFamily = publicSansFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = LabelLightSecondary
            )
        }

        if (selected) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_checkmark
                ),
                contentDescription = null,
                tint = SystemBlueLight,
                modifier = Modifier
                    .width(15.dp)
            )
        }
    }
}