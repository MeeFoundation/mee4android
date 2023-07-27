package foundation.mee.android_client.views.initial_flow

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.material.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.DeclineButton
import foundation.mee.android_client.ui.components.PrimaryButton
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.views.MeeWhiteScreen

@Composable
fun BottomMessage(icon: Painter, iconSize: Dp, title: String, message: String, onNext: () -> Unit) {
    BaseBottomMessage(icon = icon, iconSize = iconSize, title = title, message = message) {
        Button(
            onClick = onNext,
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
                    text = stringResource(R.string.continue_button_text),
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight(600),
                    color = SystemBlueLight,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun RestrictBottomMessage(
    icon: Painter,
    iconSize: Dp,
    title: String,
    message: String,
    onNextSecondaryButton: () -> Unit,
    onNextPrimaryButton: () -> Unit
) {
    BaseBottomMessage(icon = icon, iconSize = iconSize, title = title, message = message) {
        DeclineButton(
            modifier = Modifier
                .padding(0.dp)
                .height(60.dp)
                .fillMaxWidth(),
            title = stringResource(R.string.close_button_text),
            fontWeight = FontWeight(600),
            fontSize = 20.sp,
            backgroundColor = Color.Transparent
        ) {
            onNextSecondaryButton()
        }
        PrimaryButton(
            title = stringResource(R.string.settings_button_text),
            modifier = Modifier
                .padding(0.dp)
                .height(60.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight(600),
            fontSize = 20.sp,
            shape = RoundedCornerShape(size = 13.dp)
        ) {
            onNextPrimaryButton()
        }
    }
}


@Composable
fun BaseBottomMessage(
    icon: Painter,
    iconSize: Dp,
    title: String,
    message: String,
    content: @Composable () -> Unit
) {
    Surface(
        color = DurationPopupBackground,
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(bottom = 50.dp)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(iconSize)
                    .padding(top = 8.dp)
            )
            Text(
                text = title,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(700),
                color = Color.Black,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = message,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(400),
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(bottom = 64.dp)
            )
            content()
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun BottomMessagePreview() {
    MeeIdentityAgentTheme {
        Box {
            MeeWhiteScreen(modifier = Modifier.zIndex(2f), isFaded = true)
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxHeight(),
            ) {
                BottomMessage(
                    icon = painterResource(R.drawable.mee_guy_icon),
                    iconSize = 60.dp,
                    title = stringResource(id = R.string.biometry_initial_step_title),
                    message = stringResource(id = R.string.biometry_initial_step_message),
                    onNext = {}
                )
            }
        }
    }
}