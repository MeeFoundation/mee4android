package foundation.mee.android_client.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.PartnerEntryOnBackgroundColor
import foundation.mee.android_client.ui.theme.publicSansFamily


@Composable
fun Expander(
    title: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onChangeExpanded: () -> Unit,
    content: @Composable () -> Unit
) {

    Surface(
        modifier = modifier
    ) {
        Column(modifier = Modifier.clickableWithoutRipple
        { onChangeExpanded() }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = PartnerEntryOnBackgroundColor,
                )
                Icon(
                    imageVector = if (isExpanded) {
                        ImageVector.vectorResource(
                            id = R.drawable.ic_chevron_up
                        )
                    } else {
                        ImageVector.vectorResource(
                            id = R.drawable.ic_chevron_down
                        )
                    },
                    contentDescription = null,
                    tint = PartnerEntryOnBackgroundColor,
                    modifier = Modifier.width(14.dp)
                )
            }

            if (isExpanded) {
                content()
            }
        }
    }
}