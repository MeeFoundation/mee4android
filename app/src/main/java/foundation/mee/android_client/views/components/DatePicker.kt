package foundation.mee.android_client.views.components

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DatePicker(
    value: String?,
    onValueChange: (String) -> Unit = {},
    pattern: String = "yy/MM/dd",
    show: Boolean,
    onDismiss: () -> Unit
) {

    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val calendar = Calendar.getInstance()

    val date = if (value != null) {
        try {
            formatter.parse(value)
        } catch (e: java.lang.Exception) {
            Date()
        }
    } else Date()

    calendar.time = date

    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange("$year/${month}/$dayOfMonth")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )

    dialog.setButton(
        DialogInterface.BUTTON_NEGATIVE, "cancel"
    ) { _, which ->
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            onDismiss()
        }
    }

    if (show) {
        dialog.show()
    }

}