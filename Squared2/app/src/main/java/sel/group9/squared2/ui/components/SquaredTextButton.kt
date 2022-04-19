package sel.group9.squared2.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SquaredTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false
) {
    var colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    if (selected) {
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
    }

    SquaredButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(start = 12.dp, end=12.dp, top=0.dp, bottom = 4.dp),
        colors = colors
    ) {
        Text(text)
    }
}

@Composable
@Preview
fun SquaredTextButtonPreview() {
    SquaredTheme {
        SquaredTextButton("hello", onClick = {}, selected = true)
    }
}