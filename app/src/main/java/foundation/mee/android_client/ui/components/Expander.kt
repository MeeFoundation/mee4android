package foundation.mee.android_client.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.publicSansFamily


@Composable
fun Expander(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    isExpanded: Boolean,
    onChangeExpanded: () -> Unit,
    content: @Composable () -> Unit
) {

    Surface(
        modifier = modifier,
        color = color
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple
                    { onChangeExpanded() }
            ) {
                Text(
                    text = title,
                    fontFamily = publicSansFamily,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    fontWeight = FontWeight(600),
                    color = DarkText,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(id = if (isExpanded) R.string.expander_hide else R.string.expander_show),
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight(500),
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                        color = DarkText
                    )
                    Icon(
                        imageVector = if (isExpanded) {
                            ImageVector.vectorResource(
                                id = R.drawable.chevron_up
                            )
                        } else {
                            ImageVector.vectorResource(
                                id = R.drawable.chevron_down
                            )
                        },
                        contentDescription = null,
                        tint = DarkText,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
            }

            if (isExpanded) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun ExpanderPreview() {
    Expander(title = "Expander", isExpanded = false, onChangeExpanded = {}) {

    }
}