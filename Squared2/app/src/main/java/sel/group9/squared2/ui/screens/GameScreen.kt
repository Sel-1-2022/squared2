package sel.group9.squared2.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.ui.components.bottomDrawer.SquaredBottomDrawer
import sel.group9.squared2.ui.components.gameMap.GameMap
import sel.group9.squared2.ui.components.gameMap.GameMapTouchInterceptor
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

@Composable
fun GameScreen(model: SquaredGameScreenViewModel, onSettings:()->Unit) {
    SquaredBottomDrawer(model = model, onSettings = onSettings) {
        GameMapTouchInterceptor(model = model) {
            GameMap(model)
        }
    }
}

@Composable
@Preview
private fun GameScreenPreview() {
    SquaredTheme {
//        GameScreen()
    }
}