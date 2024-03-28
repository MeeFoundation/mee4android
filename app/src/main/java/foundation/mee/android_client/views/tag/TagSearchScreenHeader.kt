package foundation.mee.android_client.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.InactiveBorder

@Composable
fun TagSearchScreenHeader(
    onClickBack: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            TagSearchTopBar {
                onClickBack()
            }
        }
        Divider(
            color = InactiveBorder,
            thickness = 1.dp
        )
    }
}

@Composable
@Preview
fun PreviewTagsSearchScreenTitle() {
    TagSearchScreenHeader {}
}