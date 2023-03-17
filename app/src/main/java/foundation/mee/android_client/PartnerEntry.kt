package foundation.mee.android_client

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import uniffi.mee_agent.OidcClientMetadata

@Composable
fun PartnerEntry(
    modifier: Modifier = Modifier,
    request: ConsentRequest,
    hasEntry: Boolean = false,
) {
    val isCertified = true
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
                    painter = rememberAsyncImagePainter(model = request.clientMetadata.logoUrl),
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
                    Row(

                    ) {
                        Text(
                            text = request.clientMetadata.name,
                            style = MaterialTheme.typography.h6,
//                            modifier = modifier.line
                        )
                        if (isCertified) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.mee_certified_logo),
//                            painterResource(id = R.drawable.mee_certified_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .requiredSize(20.dp)
                            )
                        }
                    }
                    Text(
                        text = request.id,
                        style = MaterialTheme.typography.caption

                    )
                }
            }
            if (hasEntry) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .padding(end = 8.dp)
                        .size(width = 7.dp, height = 14.dp)
                ) {
                    // Insert icon from Figma
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.icon_chevron_right,
                        ), //,
                        contentDescription = "test",
                        tint = ChevronRightIconColor
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPrintConnectionSummary() {
//    val connection: MaterializedContext = MaterializedContext.RelyingParty(
//        "",
//        "connection_name",
//        RpContextData(ContextProtocol.GoogleAccount)
//    )

    MeeIdentityAgentTheme {
        PartnerEntry(
            request = ConsentRequest(from = meeContextMock),
            hasEntry = true,
//            modifier = Modifier.padding(
//                horizontal = 16.dp
//            )
        )
    }
}
