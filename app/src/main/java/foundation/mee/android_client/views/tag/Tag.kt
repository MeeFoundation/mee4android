package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.OnSecondary
import foundation.mee.android_client.ui.theme.SecondaryContainer
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun Tag(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                SecondaryContainer,
                shape
            )
            .border(
                1.dp,
                SecondaryContainer,
                shape
            )
            .padding(start = 12.dp, end = 8.dp)
            .clickableWithoutRipple {
                onClick()
            }
    ) {
        Text(
            text = "#${text}",
            fontFamily = publicSansFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
            color = OnSecondary,
            modifier = Modifier
                .padding(vertical = 6.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.closeicon
            ),
            contentDescription = null,
            tint = OnSecondary,
            modifier = Modifier
                .width(18.dp)
                .height(20.dp)
        )
    }
}

@Preview
@Composable
fun TagPreview() {
    Column {
        Tag(text = "Entertainment", onClick = {})
    }
}