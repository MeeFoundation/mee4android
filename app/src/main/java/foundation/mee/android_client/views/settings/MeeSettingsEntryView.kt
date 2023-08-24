package foundation.mee.android_client.views.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeSettingsEntry
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.ChevronRightIconColor

@Composable
fun MeeSettingsEntryView(
    entry: MeeSettingsEntry
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 64.dp),
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickableWithoutRipple { entry.onClick() }
        ) {
            Row(
                verticalAlignment = CenterVertically
            ) {
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(48.dp))
                        .size(width = 48.dp, height = 48.dp)
                        .background(Color.White)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = entry.icon
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(width = 24.dp, height = 24.dp),
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(entry.title),
                            style = MaterialTheme.typography.h6,
                        )
                    }
                    entry.description?.let {
                        Text(
                            text = stringResource(it),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
            entry.transition?.let {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.icon_chevron_right,
                    ),
                    contentDescription = null,
                    tint = ChevronRightIconColor,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(width = 9.dp, height = 16.dp)
                )
            }
        }
    }
}