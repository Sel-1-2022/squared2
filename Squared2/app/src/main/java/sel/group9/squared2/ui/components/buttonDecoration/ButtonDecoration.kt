package sel.group9.squared2.ui.components.buttonDecoration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.borderModifier

@Composable
fun ButtonDecoration(content: @Composable RowScope.() -> Unit) {
    Button(
        modifier = Modifier.then(borderModifier()),
        onClick = {},
        content = content
    )
}

@Composable
fun RedSquare() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red),
    )
}

@Composable
@Preview
fun ButtonDecorationPreview() {
    SquaredTheme {
        ButtonDecoration {
            TextField(value = "hoed", onValueChange = {})
        }
    }
}