package foundation.mee.android_client

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import foundation.mee.android_client.models.MeeAgentStore
import javax.inject.Inject

@HiltViewModel
class MeeAgentViewModel @Inject constructor(
    val meeAgentStore: MeeAgentStore
): ViewModel()
