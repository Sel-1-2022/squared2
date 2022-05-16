package sel.group9.squared2.ui.components.bottomDrawer.floatingMapButtons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.*

@Composable
fun FloatingMapButtons(onCenter: () -> Unit, resetOrientation: () -> Unit) {
    Box(
        Modifier
            .padding(bottom = 150.dp, end = 10.dp)
            .width(iconSize + border.width * 2)
    ) {
        Column (
            modifier = borderModifier()
                .background(Color.White)
                .padding(vertical = border.width),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FloatingMapButton(onClick = resetOrientation) {
                orientationMapIcon()
            }

            Divider(
                color= Color.Black,
                thickness = borderWidth / 2
            )

            FloatingMapButton(onClick = onCenter) {
                centerMapIcon()
            }
        }
    }
}

@Composable
@Preview
fun FloatingMapButtonsPreview() {
    SquaredTheme {
        FloatingMapButtons({}, {})
    }
}