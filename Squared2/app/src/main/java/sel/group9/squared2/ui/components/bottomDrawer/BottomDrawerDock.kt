package sel.group9.squared2.ui.components.bottomDrawer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.components.SquaredButton
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.iconSize
import sel.group9.squared2.ui.theme.toggleGridIcon

@Composable
fun BottomDrawerDock() {
    Row {
        SquaredButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", Modifier.size(iconSize))
        }

        Spacer(Modifier.weight(1.0f))

        SquaredButton(onClick = { /*TODO*/ }) {
            toggleGridIcon()
        }

        Spacer(Modifier.width(20.dp))

        SquaredButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Show leaderboard", Modifier.size(iconSize))
        }
    }
}

@Composable
@Preview
private fun BottomDrawerDockPreview() {
    SquaredTheme {
        BottomDrawerDock()
    }
}