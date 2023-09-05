package foundation.mee.android_client.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import foundation.mee.android_client.models.MeeSettingsEntry

@Composable
fun MeeSettingsListView(title: Int, settings: List<MeeSettingsEntry>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(settings) { entry ->
                MeeSettingsEntryView(entry)
            }
        }
    }
}