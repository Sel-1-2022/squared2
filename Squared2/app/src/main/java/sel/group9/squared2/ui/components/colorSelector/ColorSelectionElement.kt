package sel.group9.squared2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.border as squared2Border

@Composable
fun ColoredSquare(color: Color,
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
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(0.dp),
    shape: Shape = MaterialTheme.shapes.small,
    contentPadding: PaddingValues = PaddingValues()
) {
    var border: BorderStroke? = null
    var contentPadding = contentPadding
    if (selected) {
        border = squared2Border
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LocalLayoutDirection.current) + border.width,
            end = contentPadding.calculateEndPadding(LocalLayoutDirection.current) + border.width,
            top = contentPadding.calculateTopPadding() + border.width,
            bottom = contentPadding.calculateBottomPadding() + border.width
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier.then(Modifier.defaultMinSize(10.dp, 10.dp)),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        contentPadding = contentPadding
    ) {
        ColoredSquare(color = color)
    }
}
//
//@Composable
//fun ColorSelection(color: Color,
//                   selected: Boolean,
//                   size: Dp = 50.dp,
//                   modifier: Modifier = Modifier) {
//    var modifier = modifier
//    if (selected) {
//        modifier = modifier.then(borderModifier())
//    }
//
//    modifier = modifier
//        .size(size)
//        .clip(MaterialTheme.shapes.small)
//        .background(color)
//
//    Box(modifier = modifier)
//}

@Composable
@Preview
private fun colorSelectionPreview() {
    SquaredTheme {
        ColorSelection(color = Color.Red, onClick={} , selected = true)
    }
}