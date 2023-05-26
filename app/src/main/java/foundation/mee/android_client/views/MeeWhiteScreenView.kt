package foundation.mee.android_client.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.zIndex
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColorFaded
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun MeeWhiteScreen(
    isFaded: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box() {
        Surface(
            color = if (isFaded) Color.Black.copy(alpha = 0.4f) else Color.Transparent,
            modifier = Modifier.fillMaxSize().zIndex(1f)
        ) {

        }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 106.dp, end = 106.dp, top = 175.dp)
                    .zIndex(2f)
            ) {
                Icon(
                    painter = painterResource(id =R.drawable.mee_logo),
                    contentDescription = null,
                    tint = if (isFaded) MeeGreenPrimaryColorFaded else MeeGreenPrimaryColor,
                    modifier = Modifier.size(width = 163.dp, height = 48.dp)
                )
            }
    }


}

@Preview(showBackground = true)
@Composable
fun MeeWhiteScreenPreview() {
    MeeIdentityAgentTheme {
        MeeWhiteScreen()
    }
}
