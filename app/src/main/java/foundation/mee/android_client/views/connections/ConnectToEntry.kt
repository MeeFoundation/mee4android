package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeConnector
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.DurationPopupBackground
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor
import foundation.mee.android_client.ui.theme.PartnerEntryBackgroundColor

@Composable
fun ConnectToEntry(
    connector: MeeConnector,
    isLight: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isLight) {
                    DurationPopupBackground
                } else {
                    PartnerEntryBackgroundColor
                }
            )
            .sizeIn(minHeight = 56.dp)
            .padding(start = 16.dp, end = 24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://${connector.id}/favicon.ico"
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                        .clip(shape = CircleShape),
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = connector.name,
                        color = DarkText,
                        fontSize = 16.sp
                    )
                }
            }
            Row(modifier = modifier) {
                Text(
                    text = stringResource(R.string.connect_text),
                    color = MeeGreenPrimaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500)
                )
            }
        }
    }
}