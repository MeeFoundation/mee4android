package foundation.mee.android_client.views.welcome_pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.views.buttons.RejectButton

@Composable
fun ScreenScaffold(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    bottomBar: @Composable() (() -> Unit)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .weight(
                    weight = 1f,
                    fill = true
                )
        )
        bottomBar()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewScreenScaffoldEmpty() {
//    MeeIdentityAgentTheme() {
//        ScreenScaffold(image = R.drawable.welcome_screen1) {
//            Spacer(
//                modifier = Modifier
//                    .sizeIn(maxHeight = 51.dp)
//                    .fillMaxSize(),
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewScreenScaffoldContinue() {
//    MeeIdentityAgentTheme() {
//        ScreenScaffold(image = R.drawable.welcome_screen2) {
//
//            RejectButton(title = "Continue")
//        }
//    }
//}
