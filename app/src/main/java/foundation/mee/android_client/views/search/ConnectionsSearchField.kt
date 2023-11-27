package foundation.mee.android_client.views.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import foundation.mee.android_client.R
import foundation.mee.android_client.ui.components.clickableWithoutRipple
import foundation.mee.android_client.ui.theme.LabelColor
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import foundation.mee.android_client.ui.theme.PartnerEntryOnBackgroundColor
import foundation.mee.android_client.ui.theme.publicSansFamily

@Composable
fun ConnectionsSearchField(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val textFieldValue by searchViewModel.searchState.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(LabelColor.copy(alpha = 0.18f), RoundedCornerShape(10.dp))
            .padding(horizontal = 7.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_glass,
                ),
                contentDescription = null,
                tint = PartnerEntryOnBackgroundColor,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
            BasicTextField(value = textFieldValue, onValueChange = {
                searchViewModel.onChange(it)
            }, textStyle = TextStyle(
                fontFamily = publicSansFamily, fontSize = 18.sp, fontWeight = FontWeight(400),
                textAlign = TextAlign.Left
            ), modifier = Modifier.padding(start = 7.dp),
                decorationBox = { innerTextField ->
                    if (textFieldValue.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_text),
                            fontFamily = publicSansFamily,
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(400),
                            color = PartnerEntryOnBackgroundColor,
                            textAlign = TextAlign.Left
                        )
                    }
                    innerTextField()
                })
        }
        Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_mic,
            ),
            contentDescription = null,
            tint = PartnerEntryOnBackgroundColor,
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
                .clickableWithoutRipple { }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewConnectionsSearchField() {
    MeeIdentityAgentTheme {
        ConnectionsSearchField()
    }
}
