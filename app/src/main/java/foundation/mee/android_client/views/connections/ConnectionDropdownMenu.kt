package foundation.mee.android_client.views.connections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.components.crop
import foundation.mee.android_client.ui.theme.TextSecondary
import foundation.mee.android_client.ui.theme.WarningRed
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionDropdownMenu(
    isDropdownOpen: Boolean,
    onClickMenu: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_more,
            ),
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier
                .height(40.dp)
                .clickableWithoutRipple {
                    onClickMenu(!isDropdownOpen)
                }
        )
        DropdownMenu(
            expanded = isDropdownOpen,
            onDismissRequest = { onClickMenu(false) },
            modifier = Modifier
                .crop(vertical = 8.dp, horizontal = 8.dp)
                .background(color = Color.White, shape = RoundedCornerShape(4.dp))
        ) {
            DropdownMenuItem(onClick = onDelete) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector =
                        ImageVector.vectorResource(
                            id = R.drawable.trash
                        ),
                        contentDescription = null,
                        tint = WarningRed,
                        modifier = Modifier
                            .width(24.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.delete_connection),
                        fontFamily = publicSansFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarningRed,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickableWithoutRipple {
                                onClickMenu(false)
                                onDelete()
                            }
                    )

                }
            }
        }
    }
}