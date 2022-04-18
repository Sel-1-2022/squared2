package sel.group9.squared2.ui.components.bottomDrawer.floatingMapButtons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun FloatingMapButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = Modifier
            .defaultMinSize(30.dp, 30.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick,
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        content()
    }
}

@Composable
@Preview
private fun FloatingMapButtonPreview() {
    SquaredTheme {
        FloatingMapButton(onClick = {}) {
            Icon(Icons.Default.Settings, modifier = Modifier.size(50.dp), contentDescription="test")
        }
    }
}