package foundation.mee.android_client.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import foundation.mee.android_client.ui.theme.TextActive
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun MeeTopAppBar(
    title: Int,
    onClickBack: () -> Boolean
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 8.dp, top = 8.dp, start = 16.dp)
        ) {
            Icon(
                imageVector =
                ImageVector.vectorResource(
                    id = R.drawable.back
                ),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickableWithoutRipple
                    { onClickBack() }
            )
            Text(
                text = stringResource(title),
                fontFamily = publicSansFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight(600),
                color = TextActive,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}