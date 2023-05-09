package foundation.mee.android_client.views.connections

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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.utils.getURLFromString
import foundation.mee.android_client.models.meeContextMock
import foundation.mee.android_client.models.ConsentRequest
import foundation.mee.android_client.navigation.NavViewModel
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.navigation.MeeDestinations.*
import foundation.mee.android_client.ui.components.NoRippleInteractionSource

@Composable
fun PartnerEntry(
    modifier: Modifier = Modifier,
    request: ConsentRequest,
    hasEntry: Boolean = false,
    viewModel: NavViewModel = hiltViewModel()
) {
    val isCertified = true
    val navigator = viewModel.navigator
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
                        )
                        if (isCertified) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.mee_certified_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .requiredSize(20.dp)
                            )
                        }
                    }
                    Text(
                        text = getURLFromString(request.id)?.host ?: request.id,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            if (hasEntry) {
                IconButton(
                    onClick = { navigator.navigate("${MANAGE.route}/${request.clientMetadata.name}") },
                    modifier = modifier
                        .padding(end = 8.dp)
                        .size(width = 7.dp, height = 14.dp),
                    interactionSource = NoRippleInteractionSource()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.icon_chevron_right,
                        ),
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
    MeeIdentityAgentTheme {
        PartnerEntry(
            request = ConsentRequest(from = meeContextMock),
            hasEntry = true,
        )
    }
}
