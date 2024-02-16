package it.saimao.todos.presentation.home_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.saimao.todos.domain.model.Todo
import it.saimao.todos.presentation.common.taskTextStyle

@Composable
fun TodoCard(
    todo: Todo,
    onUpdate: (todo: Todo) -> Unit,
    onDone: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDone) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Task Done!",
                    modifier = Modifier.weight(2F)
                )
            }
            Text(
                text = todo.task, maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = taskTextStyle,
                modifier = Modifier
                    .weight(6F)
                    .padding(start = 12.dp)
            )
            if (todo.isImportant) {
                Icon(
                    imageVector = Icons.Rounded.Star, contentDescription = "Important task",
                    modifier = Modifier.weight(1F)
                )
            }

            IconButton(onClick = { onUpdate.invoke(todo) }, modifier = Modifier.weight(1F)) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = "Edit Task")
            }


        }
    }

}

@Preview
@Composable
fun TodoCardPreview() {

    Column {
        TodoCard(todo = Todo(task = "Clean the dishes", isImportant = true), onUpdate = {}) {
        }
        Spacer(modifier = Modifier.height(24.dp))
        TodoCard(todo = Todo(task = "Clean the dishes", isImportant = false), onUpdate = {}) {
        }
    }

}