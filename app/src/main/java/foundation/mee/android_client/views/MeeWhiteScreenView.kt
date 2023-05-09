package foundation.mee.android_client.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun MeeWhiteScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 106.dp, end = 106.dp, top = 175.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.mee_logo),
            contentDescription = null,
            tint = MeeGreenPrimaryColor,
            modifier = Modifier.size(width = 163.dp, height = 48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MeeWhiteScreenPreview() {
    MeeIdentityAgentTheme {
        MeeWhiteScreen()
    }
}
