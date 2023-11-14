package foundation.mee.android_client.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.LabelLightSecondary
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun BottomDialogHeader(
    title: Int,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(title),
            fontFamily = publicSansFamily,
            fontSize = 17.sp,
            fontWeight = FontWeight(700),
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.negative_button_text),
            fontFamily = publicSansFamily,
            fontSize = 17.sp,
            fontWeight = FontWeight(400),
            color = SystemBlueLight,
            modifier = Modifier.clickableWithoutRipple {
                onCancelClick()
            }
        )
    }
    Divider(
        color = LabelLightSecondary,
        thickness = 0.5.dp,
        modifier = Modifier.alpha(0.5f)
    )
}
