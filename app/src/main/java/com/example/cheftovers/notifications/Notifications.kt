package com.example.cheftovers.notifications

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.cheftovers.R

/**
 * Customizable AlertDialog for user error handling
 * @param title     alert header String
 * @param text      alert body String
 * @param onDismiss turns activation Boolean off upon dismissal
 */
@Composable
fun Alert(
    title: String,
    text: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.button_ok))
            }
        }
    )
}


//@Composable
//fun Toasty(
//    context: Context,
//    text: String,
//    length: Int
//) {
//    Toast.makeText(context, text, length).show()
//}