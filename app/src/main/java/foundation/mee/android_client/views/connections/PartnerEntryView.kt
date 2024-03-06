package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.models.*
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun PartnerEntry(
    connection: MeeConnection,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 64.dp),
        color = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://${connection.id}/favicon.ico"
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
                    Row {
                        Text(
                            text = connection.name,
                            color = TextActive,
                            fontFamily = publicSansFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPrintConnectionSummary() {
    MeeIdentityAgentTheme {
        PartnerEntry(
            connection = meConnectionMock
        )
    }
}
