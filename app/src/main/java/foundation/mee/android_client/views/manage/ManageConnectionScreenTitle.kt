package foundation.mee.android_client.views.manage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.PartnerEntryOnBackgroundColor
import foundation.mee.android_client.ui.theme.SystemBlueLight
import foundation.mee.android_client.ui.theme.publicSansFamily


@Composable
fun ManageConnectionScreenTitle(
    onClickBack: () -> Boolean
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .paddingFromBaseline(bottom = 10.dp)
        ) {
            Box(
                contentAlignment = BottomStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithoutRipple
                    { onClickBack() }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 9.dp)
                ) {
                    Icon(
                        imageVector =
                        ImageVector.vectorResource(
                            id = R.drawable.ic_chevron_left
                        ),
                        contentDescription = null,
                        tint = SystemBlueLight,
                        modifier = Modifier
                            .width(17.dp)
                            .height(17.dp)
                    )
                    Text(
                        text = stringResource(R.string.back_button_text),
                        fontFamily = publicSansFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = PartnerEntryOnBackgroundColor,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.manage_connection_title),
                        fontFamily = publicSansFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}