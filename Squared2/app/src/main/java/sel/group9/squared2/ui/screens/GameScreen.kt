package sel.group9.squared2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import sel.group9.squared2.ui.components.bottomDrawer.SquaredBottomDrawer
import sel.group9.squared2.ui.components.gameMap.AskLocationPermissions
import sel.group9.squared2.ui.components.gameMap.GameMap
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.viewmodel.SquaredGameMapViewModel

@Composable
fun GameScreen(model:SquaredGameMapViewModel,onSettings:()->Unit) {
    SquaredBottomDrawer(onSettings = onSettings) {
        GameMap(model)
    }
}

@Composable
@Preview
private fun GameScreenPreview() {
    SquaredTheme {
//        GameScreen()
    }
}