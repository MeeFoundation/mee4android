package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.DarkText
import foundation.mee.android_client.ui.theme.SecondaryContainer
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun TagTitle(
    text: String,
    tagsCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text,
                fontFamily = publicSansFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Left,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
                color = DarkText
            )
            Text(
                text = "$tagsCount",
                fontFamily = publicSansFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight(500),
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(SecondaryContainer, RoundedCornerShape(100.dp))
                    .sizeIn(minHeight = 16.dp, minWidth = 16.dp)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
@Preview
fun TagTitlePreview() {
    TagTitle(
        tagsCount = 4,
        text = "Filter by Tag"
    )
}