package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import foundation.mee.android_client.models.MeeConnection
import foundation.mee.android_client.models.meConnectionMock
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.TextSecondary
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionEntry(
    connection: MeeConnection,
    description: String,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isDropdownOpen by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 88.dp),
        color = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                ) {
                    Text(
                        text = connection.name,
                        color = TextActive,
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        lineHeight = 28.sp
                    )
                    Text(
                        text = description,
                        color = TextSecondary,
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.25.sp
                    )
                }
            }
            ConnectionDropdownMenu(isDropdownOpen, { isDropdownOpen = it }, onDelete)
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConnectionEntry() {
    MeeIdentityAgentTheme {
        ConnectionEntry(
            connection = meConnectionMock,
            description = "This connection has 6 data groups",
            onDelete = {}
        )
    }
}
