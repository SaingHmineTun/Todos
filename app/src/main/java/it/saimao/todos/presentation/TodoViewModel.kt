package it.saimao.todos.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.saimao.todos.data.repository.TodoRepository
import it.saimao.todos.domain.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    var todo by mutableStateOf(Todo(1, "", false))
        private set
    private var deletedTodo: Todo? = null

    val getAllTodos: Flow<List<Todo>> = repository.getAllTodos();


    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        deletedTodo = todo
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun undoDeleted() {
        deletedTodo?.let { todo ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(todo)
            }
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todo = repository.getTodoById(id)
        }
    }

    fun updateTask(newValue: String) {
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        todo = todo.copy(isImportant = newValue)
    }

    fun setUpdatedTodo(it: Todo) {
        todo = it
    }

}