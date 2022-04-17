package sel.group9.squared2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sel.group9.squared2.ui.screens.ColorSelectionRoute
import sel.group9.squared2.ui.screens.SettingsRoute
import sel.group9.squared2.ui.screens.StartRoute

@Composable
fun SquaredNavGraph (navController: NavHostController = rememberNavController(),
                     startDestination: String = SquaredAppDestinations.TITLE
) {
    NavHost(navController = navController,
        startDestination = startDestination
    ){
        composable(SquaredAppDestinations.TITLE){
            StartRoute(modelTitle = hiltViewModel(),
                onColorPressed = { navController.navigate(SquaredAppDestinations.COLOR) }) {
                navController.navigate(SquaredAppDestinations.MENU)
            }
        }
        composable(SquaredAppDestinations.COLOR){
            ColorSelectionRoute(model = hiltViewModel()) {
                navController.navigateUp()
            }
        }
        composable(SquaredAppDestinations.MENU){
            SettingsRoute(model = hiltViewModel()) {
                navController.navigateUp()
            }
        }
    }
}