package foundation.mee.android_client.views.connections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
fun ConnectionsScreenTitle() {
    TopAppBar(
        backgroundColor = MeeGreenPrimaryColor,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.connections_screen_title),
                fontFamily = publicSansFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.paddingFromBaseline(bottom = 10.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionsScreenTitlePreview() {
    MeeIdentityAgentTheme {
        ConnectionsScreenTitle()
    }
}
