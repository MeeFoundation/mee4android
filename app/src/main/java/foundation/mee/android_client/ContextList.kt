package foundation.mee.android_client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

@Composable
fun ConsentsList(
    modifier: Modifier = Modifier,
    title: String,
    contexts: List<Context> = emptyList(),
    hasEntry: Boolean = false,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = modifier.padding(horizontal = 4.dp)
        )
        LazyColumn(
//            modifier = modifier.padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = contexts) { context ->
                PartnerEntry(request = ConsentRequest(from = context), hasEntry = hasEntry)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsentsListPreview() {

    MeeIdentityAgentTheme {
        ConsentsList(title = "Sites", contexts = mobileApps)
    }
}

