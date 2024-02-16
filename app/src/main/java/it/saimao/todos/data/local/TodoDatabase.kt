package it.saimao.todos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import it.saimao.todos.domain.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}