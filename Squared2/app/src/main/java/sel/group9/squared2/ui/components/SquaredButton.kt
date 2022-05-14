package sel.group9.squared2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.sound.*
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SquaredButton(
    basics : ButtonBasics,
    colors : ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    content: @Composable RowScope.() -> Unit
) {
    basics.modifier = basics.modifier.then(Modifier.defaultMinSize(30.dp, 30.dp))
    val border = sel.group9.squared2.ui.theme.border
    basics.contentPadding = PaddingValues(
        start = basics.contentPadding.calculateStartPadding(LocalLayoutDirection.current) + border.width,
        end =basics.contentPadding.calculateEndPadding(LocalLayoutDirection.current) + border.width,
        top = basics.contentPadding.calculateTopPadding() + border.width,
        bottom = basics.contentPadding.calculateBottomPadding() + border.width
    )

    SoundButtonFactory(
        ButtonInfo(
            basics,
            getButtonComposableParts(
                elevation = ButtonDefaults.elevation(0.dp),
                shape = MaterialTheme.shapes.small,
                colors=colors),
            border = sel.group9.squared2.ui.theme.border,
            ),
        content = content
    )
}

@Composable
@Preview
fun SquaredButtonPreview() {
    SquaredTheme {
        SquaredButton(
            ButtonBasics({})
        ) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
        }
    }
}