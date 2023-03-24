package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.MeeYellowColor
import foundation.mee.android_client.views.buttons.RejectButton

@Composable
fun FirstRunPage(action: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .background(color = MeeYellowColor)
            .padding(top = 52.dp, bottom = 57.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        val painter = ImageVector.vectorResource(id = R.drawable.mee_entry)
        Image(
            painter,
            contentDescription = null,
        )
//        Image(
//            ImageVector.vectorResource(id = R.drawable.mee_entry_person),
//            contentDescription = null,
//        )
//        SvgLocalImageSample()
        RejectButton(action = action, title = "Continue")
    }
}

@Preview(showBackground = true)
@Composable
fun FirstRunPagePreview() {
    MeeIdentityAgentTheme {
        FirstRunPage()
    }
}

@Composable
fun SvgLocalImageSample() {
    val ctx = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(ctx)
            .decoderFactory(SvgDecoder.Factory())
            .data("android.resource://${ctx.applicationContext.packageName}/${R.drawable.mee_entry}")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    Image(
        painter = painter,
//        modifier = Modifier.size(100.dp),
        contentDescription = null
    )
}

