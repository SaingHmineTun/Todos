package it.saimao.todos

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import it.saimao.todos.presentation.TodoViewModel
import it.saimao.todos.presentation.home_screen.HomeScreen
import it.saimao.todos.presentation.update_screen.UpdateScreen

object Destinations {
    const val HOME = "home"
    const val DETAIL = "detail"
}

@Composable
fun NavCompose(
    viewModel: TodoViewModel
) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.HOME) {
        composable(Destinations.HOME) {
            HomeScreen(viewModel = viewModel, onUpdate = {
                viewModel.setUpdatedTodo(it)
                navController.navigate(Destinations.DETAIL)
            })
        }
        composable(
            route = Destinations.DETAIL,
//            arguments = listOf(navArgument("id") {
//                type = NavType.IntType
//            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }, exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            UpdateScreen(todo = viewModel.todo, onUpdate = {
                viewModel.updateTodo(it)
            }) {
                navController.popBackStack()
            }
        }

    }
}