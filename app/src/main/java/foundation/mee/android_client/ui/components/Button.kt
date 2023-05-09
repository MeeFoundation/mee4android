package foundation.mee.android_client.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.PartnerEntryBackgroundColor
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(MeeGreenPrimaryColor),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        interactionSource = NoRippleInteractionSource()
    ) {
        Text(
            text = title,
            color = Color.White,
            fontFamily = publicSansFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RejectButton(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(PartnerEntryBackgroundColor),
        modifier = modifier,
        interactionSource = NoRippleInteractionSource()
    ) {
        Text(
            text = title,
            color = MeeGreenPrimaryColor,
            fontFamily = publicSansFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            textAlign = TextAlign.Center
        )
    }
}