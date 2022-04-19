package sel.group9.squared2.ui.components.bottomDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.components.bottomDrawer.floatingMapButtons.FloatingMapButton
import sel.group9.squared2.ui.theme.*

@Composable
fun FloatingMapButtons() {
    Box(
        Modifier
            .padding(bottom = 150.dp, end = 10.dp)
            .width(50.dp + border.width * 2)
    ) {
        Column (
            modifier = borderModifier()
                .background(Color.White)
                .padding(vertical = border.width),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FloatingMapButton(onClick = {}) {
                orientationMapIcon()
            }

            Divider(
                color= Color.Black,
                thickness = borderWidth / 2
            )

            FloatingMapButton(onClick = {}) {
                centerMapIcon()
            }
        }
    }
}

@Composable
@Preview
fun FloatingMapButtonsPreview() {
    SquaredTheme {
        FloatingMapButtons()
    }
}