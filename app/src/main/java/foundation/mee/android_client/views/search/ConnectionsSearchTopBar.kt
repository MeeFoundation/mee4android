package foundation.mee.android_client.views.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun ConnectionsSearchTopBar(
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 3.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector =
            ImageVector.vectorResource(
                id = R.drawable.back
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
                .clickableWithoutRipple
                { onCancelClick() }
        )
        ConnectionsSearchField(
            modifier = Modifier
                .weight(1f)
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
