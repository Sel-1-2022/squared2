package sel.group9.squared2.sound

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import sel.group9.squared2.MainActivity
import sel.group9.squared2.data.SoundManager
import sel.group9.squared2.viewmodel.SquaredMediaViewModel

@Composable
fun SoundButton(
    model : SquaredMediaViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit

) {
    Button(
        onClick = {
            model.playButton()
            onClick()
                  },
        modifier = modifier.then(Modifier.defaultMinSize(10.dp, 10.dp)),
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content=content
    )
}