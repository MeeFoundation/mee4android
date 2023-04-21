package foundation.mee.android_client.views.wizard_pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun LoadingScreenWhitePage(
    progress: Float,
    fillTextBar: Boolean = true,
    text: String?,
    modifierBox: Modifier = Modifier,
    modifierLogo: Modifier = Modifier,
    modifierCircle: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (fillTextBar) Arrangement.spacedBy(15.dp) else Arrangement.Center,
//        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(278f))
        Row() {

            Spacer(modifier = Modifier.weight(1f))
            MeeLogoWithCircleIndicator(
                progress = progress,
                modifierBox = modifierBox,
                modifierLogo = modifierLogo,
                modifierCircle = modifierCircle.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        
        

        if (fillTextBar) {
//            Spacer(modifier = Modifier.weight(15f))
            if (!text.isNullOrEmpty()) {
                Text(
                    text = text,
                    fontSize = 18.sp,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .sizeIn(maxWidth = 220.dp,
                            maxHeight = 42.dp
                        )
//                        .weight(42f)
                        .zIndex(2f)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .sizeIn(maxWidth = 220.dp,
                            maxHeight = 42.dp
                        )
                        .weight(42f)
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(257f)
                    .zIndex(1f))
        }
        else {
            Spacer(modifier = Modifier.weight(314f))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenWhitePagePreview() {
    MeeIdentityAgentTheme() {
        LoadingScreenWhitePage(
            progress = 0.75f,
            text = stringResource(id = R.string.loading_white_page_1)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenWhitePageNullPreview() {
    MeeIdentityAgentTheme() {
        LoadingScreenWhitePage(
            progress = 0.95f,
            text = null
        )
    }
}
