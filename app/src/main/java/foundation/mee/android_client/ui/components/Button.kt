package foundation.mee.android_client.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import foundation.mee.android_client.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

@Composable
fun MainButton(
    action: () -> Unit = {}
) {
    Button(
        onClick = action,
        shape = RoundedCornerShape(size = 13.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .padding(0.dp)
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Surface(color = Color.White) {
            Text(
                text = "Continue",
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(600),
                color = SystemBlueLight,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MeeCertifiedButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickableWithoutRipple {
                onClick()
            }) {
        Image(
            painter = painterResource(R.drawable.mee_certified_logo),
            contentDescription = null,
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .padding(end = 5.dp)
        )
        Text(
            text = "Mee-certified?",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight(500),
            color = MeeBrand,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
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
