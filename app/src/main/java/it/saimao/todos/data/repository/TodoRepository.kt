package it.saimao.todos.data.repository

import it.saimao.todos.data.local.TodoDao
import it.saimao.todos.domain.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    val dao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)
    suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo)
    suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)
    suspend fun getTodoById(id: Int) = dao.getTodoById(id)
    fun getAllTodos(): Flow<List<Todo>> = dao.getAllTodos()
}