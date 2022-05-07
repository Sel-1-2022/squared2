package sel.group9.squared2.sound

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SoundButtonFactory(onClick: () -> Unit,
                       modifier: Modifier = Modifier,
                       elevation: ButtonElevation? = ButtonDefaults.elevation(),
                       shape: Shape = MaterialTheme.shapes.small,
                       border: BorderStroke? = null,
                       colors: ButtonColors = ButtonDefaults.buttonColors(),
                       contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
                       content: @Composable RowScope.() -> Unit){
    SoundButton(hiltViewModel(), onClick,modifier,elevation,shape,border,colors,contentPadding,content)
}