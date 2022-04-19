package sel.group9.squared2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.sound.SoundButton
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SquaredButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(0.dp),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = sel.group9.squared2.ui.theme.border,
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit
) {
    var contentPadding = contentPadding
    if (border != null) {
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LocalLayoutDirection.current) + border.width,
            end = contentPadding.calculateEndPadding(LocalLayoutDirection.current) + border.width,
            top = contentPadding.calculateTopPadding() + border.width,
            bottom = contentPadding.calculateBottomPadding() + border.width
        )
    }

    SoundButton(
        onClick = onClick,
        modifier = modifier.then(Modifier.defaultMinSize(30.dp, 30.dp)),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
@Preview
fun SquaredButtonPreview() {
    SquaredTheme {
        SquaredButton(onClick = {}) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
        }
    }
}