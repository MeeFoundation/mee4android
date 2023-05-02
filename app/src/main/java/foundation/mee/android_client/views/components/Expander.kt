package foundation.mee.android_client.views.consent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
        Column(modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
        { onChangeExpanded() }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 22.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = publicSansFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = PartnerEntryOnBackgroundColor,
                    modifier = Modifier.padding(bottom = 22.dp)
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

/*
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ExpanderPreview() {
    MeeIdentityAgentTheme {
        Expander(
            "Required"
        ) {
            consentRequestMock.claims.filter { x -> x.isRequired }.forEach { x ->
                ConsentEntry(x, true) {}
            }
        }
    }
}*/
