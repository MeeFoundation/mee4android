package foundation.mee.android_client.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.SecondaryContainer
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontFamily = publicSansFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight(500),
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(SecondaryContainer, RoundedCornerShape(100.dp))
            .sizeIn(minHeight = 16.dp, minWidth = 16.dp)
            .padding(horizontal = 4.dp)
    )
}