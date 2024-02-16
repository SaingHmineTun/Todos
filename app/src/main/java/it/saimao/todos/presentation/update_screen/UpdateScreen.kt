package it.saimao.todos.presentation.update_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.saimao.todos.domain.model.Todo
import it.saimao.todos.presentation.TodoViewModel

@Composable
fun UpdateScreen(
    todo: Todo,
    onUpdate: (todo: Todo) -> Unit,
    onBack: () -> Unit
) {

    var todoState by remember {
        mutableStateOf(todo)
    }
//    LaunchedEffect(key1 = true, block = viewModel.getTodoById(todo.id))

    Box(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier.padding(8.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = todoState.task,
                    onValueChange = {
                        todoState = todoState.copy(task = it)
                    },
                    modifier = Modifier.fillMaxWidth(9F),
                    label = {
                        Text(text = "Task", fontFamily = FontFamily.Monospace)
                    },
                    shape = RectangleShape,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {

                    Checkbox(checked = todoState.isImportant, onCheckedChange = {
                        todoState = todoState.copy(isImportant = it)
                    })
                    Text(text = "Important", fontFamily = FontFamily.Monospace, fontSize = 18.sp)
                }
                Button(onClick = {
                    onUpdate.invoke(todoState)
                    onBack()
                }, shape = RoundedCornerShape(8.dp), modifier = Modifier.width(125.dp)) {
                    Text(text = "Update", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }
    }


}

@Preview(showSystemUi = true)
@Composable
fun UpdateScreenPreview() {
    UpdateScreen(todo = Todo(1, "Clear this task", false), onUpdate = {

    }) {

    }
}