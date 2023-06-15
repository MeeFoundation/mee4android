package foundation.mee.android_client.views.initial_flow

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.views.MeeWhiteScreen
import foundation.mee.android_client.views.animations.ConsentPageAnimation

@Composable
fun BottomMessage(icon: Painter, iconSize: Dp, title: String, message: String, onNext: () -> Unit) {
    Surface(color = DurationPopupBackground,
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 13.dp , topStart = 13.dp))) {
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
            Text(text = title,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(700),
                color = Color.Black,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp))
            Text(text = message,
                fontFamily = publicSansFamily,
                fontWeight = FontWeight(400),
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(bottom = 64.dp)
            )
            Button(onClick = onNext,
                shape = RoundedCornerShape(size = 13.dp),
                elevation = null,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(0.dp)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Surface(color = Color.White) {
                    Text(text = "Continue",
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight(600),
                        color = SystemBlueLight,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,

                    )
                }

            }
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
                    title = "Set up your Privacy Agent",
                    message = "To store your data, Mee contains a secure data vault, which is protected by Biometry. Please set up Biometry.",
                    onNext = {}
                )
            }
        }
    }
}