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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import foundation.mee.android_client.R
import foundation.mee.android_client.models.MeeSettingsEntry
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.IconBackground
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun MeeSettingsEntryView(
    entry: MeeSettingsEntry
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(1f)
            .sizeIn(minHeight = 64.dp),
        color = Color.Transparent,
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
                        .size(width = 40.dp, height = 40.dp)
                        .background(IconBackground)
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
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(entry.title),
                            color = TextActive,
                            fontFamily = publicSansFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                        )
                    }
                    entry.description?.let {
                        Text(
                            text = stringResource(it),
                            color = DarkText,
                            fontFamily = publicSansFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                        )
                    }
                }
            }
            entry.transition?.let {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = entry.iconEnd,
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(width = 24.dp, height = 24.dp)
                )
            }
        }
    }
}