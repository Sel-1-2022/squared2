package sel.group9.squared2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SquaredTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    SquaredButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(start = 12.dp, end=12.dp, top=0.dp, bottom = 4.dp),
    ) {
        Text(text)
    }
}

@Composable
@Preview
fun SquaredTextButtonPreview() {
    SquaredTheme {
        SquaredTextButton("hello", onClick = {})
    }
}