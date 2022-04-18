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
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.border
import sel.group9.squared2.ui.theme.borderModifier
import sel.group9.squared2.ui.theme.borderWidth

@Composable
fun FloatingMapButtons() {
    Box(
        Modifier
            .width(IntrinsicSize.Min)
            .padding(bottom = 25.dp)
    ) {
        Column (
            modifier = borderModifier()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FloatingMapButton(onClick = {}) {
                Icon(Icons.Default.Add, modifier = Modifier.size(50.dp), contentDescription="test")
            }

            Divider(
                color= Color.Black,
                thickness = borderWidth / 2
            )

            FloatingMapButton(onClick = {}) {
                Icon(Icons.Default.Add, modifier = Modifier.size(50.dp), contentDescription="test")
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