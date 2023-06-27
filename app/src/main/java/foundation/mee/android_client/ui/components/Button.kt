package foundation.mee.android_client.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.PartnerEntryBackgroundColor
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    title: String,
    fontWeight: FontWeight = FontWeight(700),
    fontSize: TextUnit = 18.sp,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(MeeGreenPrimaryColor),
        shape = shape,
        modifier = modifier,
        interactionSource = NoRippleInteractionSource()
    ) {
        Text(
            text = title,
            color = Color.White,
            fontFamily = publicSansFamily,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DeclineButton(
    modifier: Modifier = Modifier,
    title: String,
    fontWeight: FontWeight = FontWeight(700),
    fontSize: TextUnit = 18.sp,
    backgroundColor: Color = PartnerEntryBackgroundColor,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = modifier,
        interactionSource = NoRippleInteractionSource()
    ) {
        Text(
            text = title,
            color = MeeGreenPrimaryColor,
            fontFamily = publicSansFamily,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RejectButton(
    title: String,
    action: () -> Unit = {}
) {
    Button(
        onClick = action,
        border = BorderStroke(2.dp, MeeGreenPrimaryColor),
        shape = AbsoluteRoundedCornerShape(10.dp),
        modifier = Modifier
            .sizeIn(maxHeight = 51.dp)
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(
            text = title,
            color = MeeGreenPrimaryColor,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            letterSpacing = 0.45.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RejectButtonPreview() {
    MeeIdentityAgentTheme() {
        RejectButton(title = "Get Started")
    }
}
