package it.saimao.todos.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.saimao.todos.domain.model.Todo
import it.saimao.todos.presentation.TodoViewModel
import it.saimao.todos.presentation.common.mySnackBar
import it.saimao.todos.presentation.common.topAppBarTextStyle
import it.saimao.todos.presentation.home_screen.components.AlertDialogHs
import it.saimao.todos.presentation.home_screen.components.EmptyTaskScreen
import it.saimao.todos.presentation.home_screen.components.TodoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TodoViewModel, onUpdate: (todo: Todo) -> Unit) {
    val todos by viewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList())
    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(title = {
                Text(text = "Todo", style = topAppBarTextStyle)
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openDialog = true }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add New Task")
            }
        }
    ) { paddingValues ->
        if (openDialog) {
            AlertDialogHs(
                openDialog = openDialog,
                onClose = { openDialog = false },
                viewModel = viewModel
            )
        }

        if (todos.isEmpty()) {
            EmptyTaskScreen(paddingValues = paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(todos, key = { it.id }) { todo ->
                    TodoCard(todo = todo, onUpdate = {
                        onUpdate(it)
                    }) {
                        viewModel.deleteTodo(todo)
                        mySnackBar(
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                            msg = "DELETED SUCCESSFULLY!",
                            actionLabel = "UNDO",
                            onAction = { viewModel.undoDeleted() })
                    }
                }
            }
        }
    }

}