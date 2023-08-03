package foundation.mee.android_client.views.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.models.*
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun PartnerEntry(
    connection: MeeConnection,
    modifier: Modifier = Modifier,
    hasEntry: Boolean = false
) {

    val state = rememberPartnerEntryState(connection)

    Surface(
        modifier = modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 64.dp),
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
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
                        model = state.logoUri
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 48.dp, height = 48.dp)
                        .clip(shape = CircleShape),
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = state.name,
                            style = MaterialTheme.typography.h6,
                        )
                        Image(
                            painter = rememberAsyncImagePainter(R.drawable.mee_certified_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .requiredSize(20.dp)
                        )
                    }
                    Text(
                        text = state.hostname,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            if (hasEntry) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_chevron_right,
                    ),
                    contentDescription = null,
                    tint = ChevronRightIconColor,
                    modifier = modifier
                        .padding(end = 8.dp)
                        .size(width = 9.dp, height = 16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPrintConnectionSummary() {
    MeeIdentityAgentTheme {
        PartnerEntry(
            connection = meConnectionMock,
            hasEntry = true,
        )
    }
}
