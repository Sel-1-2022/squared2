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

data class ButtonBasics(var onClick: () -> Unit,
                        var modifier : Modifier = Modifier,
                        var contentPadding: PaddingValues = PaddingValues())

data class ButtonInfo(  var basic : ButtonBasics,
                        var composableParts: ButtonComposableParts,
                        var border: BorderStroke? = null
                        )

data class ButtonComposableParts(   var colors:ButtonColors,
                                    var shape : Shape,
                                    var elevation: ButtonElevation?)

@Composable
fun getButtonComposableParts(
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.buttonColors()):ButtonComposableParts

    =ButtonComposableParts(colors,shape,elevation)



@Composable
fun SoundButton(
    model : SquaredMediaViewModel,
    info : ButtonInfo,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = {
            model.playButton()
            info.basic.onClick()
                  },
        modifier = info.basic.modifier.then(Modifier.defaultMinSize(10.dp, 10.dp)),
        elevation = info.composableParts.elevation,
        shape = info.composableParts.shape,
        border = info.border,
        colors = info.composableParts.colors,
        contentPadding = info.basic.contentPadding,
        content=content
    )
}