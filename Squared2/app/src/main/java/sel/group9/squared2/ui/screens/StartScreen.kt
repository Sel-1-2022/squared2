package sel.group9.squared2.ui.screens

import androidx.compose.material.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.viewmodel.SquaredTitleViewModel
import sel.group9.squared2.ui.components.SquaredButton
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.components.SquaredTextField
import sel.group9.squared2.ui.theme.SquaredTheme


@Composable
fun StartRoute(modelTitle: SquaredTitleViewModel, onColorPressed:()->Unit, onCogPressed:()->Unit){
    val color = modelTitle.color
    StartScreen(color = color, onColorPressed = onColorPressed,onCogPressed = onCogPressed)
}

@Composable
fun StartScreen(color: Color,onColorPressed:()->Unit,onCogPressed:()->Unit) {
    val currentName = "name"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            SquaredButton(modifier = Modifier.padding(all = 0.dp), onClick = onCogPressed) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
            }
            Spacer(Modifier.width(30.dp))
        }

        Spacer(Modifier.weight(0.8f))

        Text("squared²", style = MaterialTheme.typography.h1)

        Spacer(Modifier.height(60.dp))

        ColorSelection(
            color = color,
            selected = true,
            onClick = onColorPressed
        )

        Spacer(Modifier.height(20.dp))

        SquaredTextField(value = currentName, onValueChange = {})

        Spacer(Modifier.height(60.dp))

        SquaredTextButton("play", onClick = {})

        Spacer(Modifier.weight(1.0f))
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    SquaredTheme {
        StartScreen(Color.Red,{},{})
    }
}