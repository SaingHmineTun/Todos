package it.saimao.todos.presentation.common

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun mySnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    msg: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        val snackbarResult = snackbarHostState.showSnackbar(
            message = msg,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )
        when (snackbarResult) {
            SnackbarResult.ActionPerformed -> onAction.invoke()
            SnackbarResult.Dismissed -> {}
        }
    }
}


fun toastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
