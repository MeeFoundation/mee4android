package foundation.mee.android_client.views.welcome_pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeGreenPrimaryColor

@Composable
fun WelcomePageIconButton(
    @DrawableRes imageIcon: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    IconButton(
        onClick = action,
        modifier = modifier
            .size(width = 7.dp, height = 12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = imageIcon,
            ),
            contentDescription = null,
            tint = MeeGreenPrimaryColor,
        )
    }
}

@Composable
fun WelcomePageIconBullet(
    @DrawableRes imageIcon: Int,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = ImageVector.vectorResource(
            id = imageIcon,
        ),
        contentDescription = null,
        tint = MeeGreenPrimaryColor,
        modifier = modifier
            .size(width = width, height = height)
    )
}

