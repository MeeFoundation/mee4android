package foundation.mee.android_client.views.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionsSearchTopBar(
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MeeGreenPrimaryColor)
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ConnectionsSearchField(
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = stringResource(R.string.negative_button_text),
            fontFamily = publicSansFamily,
            fontSize = 17.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight(400),
            color = Color.White,
            modifier = Modifier
                .padding(start = 16.dp)
                .clickableWithoutRipple {
                    onCancelClick()
                }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConnectionsSearchTopBar() {
    MeeIdentityAgentTheme {
        ConnectionsSearchTopBar {}
    }
}
