package foundation.mee.android_client.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.DarkText
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
            .padding(horizontal =  24.dp)
            .padding(bottom = 28.dp,top = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(title),
            fontFamily = publicSansFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            color = DarkText
        )
        Icon(imageVector = ImageVector.vectorResource(
            id = R.drawable.closeicon
        ),
            contentDescription = null,
            tint = DarkText,
            modifier = Modifier
                .width(24.dp)
                .clickableWithoutRipple {
                    onCancelClick()
                })


    }
//    Divider(
//        color = LabelLightSecondary,
//        thickness = 0.5.dp,
//        modifier = Modifier.alpha(0.5f)
//    )
}
