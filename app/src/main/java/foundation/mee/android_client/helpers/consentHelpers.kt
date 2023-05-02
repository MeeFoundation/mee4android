package foundation.mee.android_client.helpers

import foundation.mee.android_client.R
import foundation.mee.android_client.models.ConsentEntryType

fun getConsentEntryIconByType(entryType: ConsentEntryType): Int {
    return when (entryType) {
        ConsentEntryType.string -> R.drawable.ic_person
        ConsentEntryType.email -> R.drawable.ic_letter
        ConsentEntryType.card -> R.drawable.ic_card
        ConsentEntryType.date -> R.drawable.ic_calendar
        else -> R.drawable.ic_key
    }
}