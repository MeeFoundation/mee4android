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
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.TextSecondary

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
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithoutRipple { onClick() }
            .padding(vertical = 8.dp)

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                fontFamily = publicSansFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = TextActive
            )
            Text(
                text = description,
                fontFamily = publicSansFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = TextSecondary,
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (selected) R.drawable.radio_selected else R.drawable.radio_empty
            ),
            contentDescription = null,
            tint = MeeGreenPrimaryColor,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
    }
}