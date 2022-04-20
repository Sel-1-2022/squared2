package sel.group9.squared2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import sel.group9.squared2.MainActivity
import sel.group9.squared2.ui.screens.ColorSelectionRoute
import sel.group9.squared2.ui.screens.GameScreen
import sel.group9.squared2.ui.screens.SettingsRoute
import sel.group9.squared2.ui.screens.StartRoute

@Composable
fun SquaredNavGraph (activity : MainActivity,
                     fusedLocationClient: FusedLocationProviderClient,
                     navController: NavHostController = rememberNavController(),
                     startDestination: String = SquaredAppDestinations.TITLE
) {
    NavHost(navController = navController,
        startDestination = startDestination
    ){
        composable(SquaredAppDestinations.TITLE){
            StartRoute(
                modelTitle = hiltViewModel(),
                onStart = { navController.navigate(SquaredAppDestinations.PLAY) },
                onColorPressed = { navController.navigate(SquaredAppDestinations.COLOR) },
                onCogPressed = {navController.navigate(SquaredAppDestinations.MENU) }
            )
        }
        composable(SquaredAppDestinations.COLOR){
            ColorSelectionRoute(model = hiltViewModel()) {
                navController.navigateUp()
            }
        }
        composable(SquaredAppDestinations.MENU){
            SettingsRoute(activity,model = hiltViewModel()) {
                navController.navigateUp()
            }
        }
        composable(SquaredAppDestinations.PLAY){
            GameScreen(fusedLocationClient)
        }
    }
}