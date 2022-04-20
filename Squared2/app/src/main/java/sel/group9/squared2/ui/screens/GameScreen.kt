package sel.group9.squared2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.location.FusedLocationProviderClient
import sel.group9.squared2.ui.components.bottomDrawer.SquaredBottomDrawer
import sel.group9.squared2.ui.components.gameMap.GameMap
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun GameScreen(fusedLocationClient: FusedLocationProviderClient,onSettings:()->Unit) {
    SquaredBottomDrawer(onSettings = onSettings) {
        GameMap(fusedLocationClient)
    }
}

@Composable
@Preview
private fun GameScreenPreview() {
    SquaredTheme {
//        GameScreen()
    }
}