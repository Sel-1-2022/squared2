package sel.group9.squared2.ui.components.gameMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun GameMap() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    )
}

@Composable
@Preview
fun GameMapPreview() {

}