package sel.group9.squared2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.border
import sel.group9.squared2.ui.theme.borderModifier

@Composable
@Preview
fun redIcon() {
    SquaredTheme {
        ColorSelection(color = Color.Red, selected = true)
    }
}

@Composable
fun ColorSelection(color: Color,
                   selected: Boolean,
                   modifier: Modifier = Modifier) {
    var modifier = modifier
    if (selected) {
        modifier = modifier.then(borderModifier())
    }

    modifier = modifier
        .size(50.dp)
        .background(color)

    return Box(modifier = modifier)
}