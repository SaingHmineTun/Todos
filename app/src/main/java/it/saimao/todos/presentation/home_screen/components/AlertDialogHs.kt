package it.saimao.todos.presentation.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import it.saimao.todos.domain.model.Todo
import it.saimao.todos.presentation.TodoViewModel
import it.saimao.todos.presentation.common.toastMessage
import kotlinx.coroutines.job

@Composable
fun AlertDialogHs(
    openDialog: Boolean,
    onClose: () -> Unit,
    viewModel: TodoViewModel
) {
    var text by remember { mutableStateOf("") }
    var isImportant by remember {
        mutableStateOf(false)
    }
    val todo = Todo(task = text, isImportant = isImportant)
    val focusRequester = FocusRequester()
    val context = LocalContext.current

    fun closeDialog() {
        text = ""
        isImportant = false
        onClose.invoke()
    }

    fun addTodo() {
        if (text.isNotBlank()) {
            viewModel.insertTodo(todo)
            closeDialog()
        } else {
            toastMessage(context, "Empty Task!")
        }
    }

    if (openDialog) {
        AlertDialog(
            title = {
                Text(text = "TODO", fontFamily = FontFamily.Serif)
            },
            text = {
                LaunchedEffect(key1 = true, block = {
                    coroutineContext.job.invokeOnCompletion { focusRequester.requestFocus() }
                })
                Column {
                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        placeholder = {
                            Text(text = "Add Task", fontFamily = FontFamily.Monospace)
                        },
                        shape = RectangleShape,
                        modifier = Modifier.focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                addTodo()
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear Task Text"
                                )
                            }
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Is Important",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp
                        )
                        Checkbox(checked = isImportant, onCheckedChange = {
                            isImportant = it
                        })
                    }
                }
            },
            onDismissRequest = { closeDialog() },
            dismissButton = {
                OutlinedButton(onClick = { closeDialog() }) {
                                Text(text = "Close")
                }
            },
            confirmButton = {
                Button(onClick = {
                    addTodo()
                }) {
                    Text(text = "Save")
                }
            })
    }
}