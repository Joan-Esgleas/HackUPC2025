import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gardenappdefinitive.GardenScreen
import com.tu_paquete.ui.WelcomeScreen

@Composable
fun GardenApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcomeScreen"
    ) {
        composable("welcomeScreen") {
            WelcomeScreen(navController = navController)
        }
        composable("GardenScreen") {
            GardenScreen(navController = navController)
        }
    }
}

