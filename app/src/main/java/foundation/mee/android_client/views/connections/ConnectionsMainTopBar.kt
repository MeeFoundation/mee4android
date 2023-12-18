package foundation.mee.android_client.views.connections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.theme.GrayText
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionsMainTopBar(
    onClickMenu: () -> Unit,
    onClickSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(imageVector = ImageVector.vectorResource(
            id = R.drawable.menu
        ),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .width(24.dp)
                .clickable { onClickMenu() })


        Text(
            text = stringResource(R.string.connections_screen_title),
            fontFamily = publicSansFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight(600),
            color = GrayText,
            textAlign = TextAlign.Center,
        )


        Icon(imageVector = ImageVector.vectorResource(
            id = R.drawable.search
        ),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .width(24.dp)
                .clickable { onClickSearch() })
    }
}