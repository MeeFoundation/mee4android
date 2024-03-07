package foundation.mee.android_client.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import foundation.mee.android_client.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.*

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
    backgroundColor: Color = Color.White,
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

@Composable
fun PopupButton(
    title: String,
    action: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .sizeIn(maxHeight = 40.dp)
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .clickable(onClick = action),

    ) {
        Text(
            text = title,
            color = MeeGreenPrimaryColor,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            letterSpacing = 0.1.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RejectButtonPreview() {
    MeeIdentityAgentTheme {
        RejectButton(title = stringResource(R.string.get_started_button_title))
    }
}

@Preview(showBackground = true)
@Composable
fun PopupButtonPreview() {
    MeeIdentityAgentTheme {
        PopupButton(title = stringResource(R.string.get_started_button_title))
    }
}
