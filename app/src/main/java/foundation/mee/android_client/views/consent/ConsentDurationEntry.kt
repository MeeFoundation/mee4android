package foundation.mee.android_client.views.consent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foundation.mee.android_client.ui.theme.ChevronRightIconColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.publicSansFamily
import foundation.mee.android_client.R

@Composable
fun ConsentDurationEntry(
    text: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Text(
                text = text,
                modifier = Modifier.padding(start = 11.dp),
                fontFamily = publicSansFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight(700),
                color = Color.Black
            )
            Text(
                text = description,
                modifier = Modifier.padding(start = 11.dp),
                fontFamily = publicSansFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight(700),
                color = ChevronRightIconColor
            )
        }

        if (selected) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_checkmark
                ),
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.width(15.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun ConsentDurationEntryPreview() {
    MeeIdentityAgentTheme {
        ConsentDurationEntry(
            ConsentDurationOptions[0].name,
            ConsentDurationOptions[0].description,
            true
        ) {}
    }
}