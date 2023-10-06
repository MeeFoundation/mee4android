package foundation.mee.android_client.views.connections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import foundation.mee.android_client.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionsScreenTitle(
    onClickMenu: () -> Unit
) {
    TopAppBar(
        backgroundColor = MeeGreenPrimaryColor,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 10.dp, start = 12.dp)
            ) {
                Icon(imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_sidebar_menu
                ),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(18.dp)
                        .clickable { onClickMenu() })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .paddingFromBaseline(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(R.string.connections_screen_title),
                    fontFamily = publicSansFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight(600),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionsScreenTitlePreview() {
    MeeIdentityAgentTheme {
        ConnectionsScreenTitle {}
    }
}
