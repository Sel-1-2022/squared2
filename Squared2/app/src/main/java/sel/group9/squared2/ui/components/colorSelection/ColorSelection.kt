package sel.group9.squared2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sel.group9.squared2.sound.SoundButtonFactory
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.border as squared2Border

@Composable
private fun ColoredSquare(color: Color,
                          size: Dp = 50.dp
) {
    val modifier = Modifier
        .size(size)
        .clip(MaterialTheme.shapes.small)
        .background(color)

    Box(modifier = modifier)
}

@Composable
fun ColorSelection(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    contentPadding: PaddingValues = PaddingValues()
) {
    var border: BorderStroke? = null
    if (selected) {
        border = squared2Border
    }

    SoundButtonFactory(
        onClick = onClick,
        modifier = modifier.then(Modifier.defaultMinSize(10.dp, 10.dp)),
        border = border,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        content= {ColoredSquare(color = color)},
        contentPadding = contentPadding)

}

@Composable
@Preview
private fun colorSelectionPreview() {
    SquaredTheme {
        ColorSelection(color = Color.Red, onClick={} , selected = true)
    }
}