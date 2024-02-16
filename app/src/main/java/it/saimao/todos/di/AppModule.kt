package it.saimao.todos.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.saimao.todos.data.local.TodoDao
import it.saimao.todos.data.local.TodoDatabase
import it.saimao.todos.data.repository.TodoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, name = "todo_database")
            .build()
    }

    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()

    @Provides
    fun provideTodoRepository(dao: TodoDao) = TodoRepository(dao)

}