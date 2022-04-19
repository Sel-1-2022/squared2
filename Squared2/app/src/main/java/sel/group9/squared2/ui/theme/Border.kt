package sel.group9.squared2.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val borderWidth = 5.dp
val border = BorderStroke(borderWidth, Color.Black)

@Composable
fun borderModifier(): Modifier = Modifier
    .border(
        border = border,
        shape = MaterialTheme.shapes.small
    )
    .clip(MaterialTheme.shapes.small)