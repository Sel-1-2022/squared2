package sel.group9.squared2.ui.screens

import androidx.compose.material.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.viewmodel.SquaredTitleViewModel
import sel.group9.squared2.ui.components.SquaredButton
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.components.SquaredTextField
import sel.group9.squared2.ui.components.gameMap.AskLocationPermissions
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.colorList
import sel.group9.squared2.ui.theme.errorTextStyle


@Composable
fun StartRoute(modelTitle: SquaredTitleViewModel, onColorPressed:()->Unit, onCogPressed:()->Unit, onStart:()->Unit){
    StartScreen(
        StartState(
            StartInput(modelTitle.input.collectAsState().value) { x ->
                modelTitle.changeInput(
                    x
                )
            },
            StartColor(value = colorList[modelTitle.color().collectAsState().value],onColorPressed),error =modelTitle.error.collectAsState().value)
        ,onCogPressed = onCogPressed,
        onStart = {
            modelTitle.commit(onStart)
        })
}
data class StartInput(val name:String, val onChange: (String) -> Unit)
data class StartColor(val value: Color, val onColorPressed: () -> Unit)
data class StartState(val input: StartInput, val color : StartColor, val error: String)
@Composable
fun StartScreen(state: StartState,
                onCogPressed: ()->Unit,
                onStart: ()->Unit) {
    AskLocationPermissions {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                SquaredButton(modifier = Modifier.padding(all = 0.dp).testTag("settings"), onClick = onCogPressed) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings", Modifier.size(40.dp))
                }
                Spacer(Modifier.width(30.dp))
            }

            Spacer(Modifier.weight(0.8f))

            Text("squared²", style = MaterialTheme.typography.h1)

            Spacer(Modifier.height(60.dp))

            ColorSelection(modifier=Modifier.testTag("Color"), color = state.color.value, selected = true, onClick = state.color.onColorPressed)

            Spacer(Modifier.height(20.dp))

            SquaredTextField(
                value = state.input.name,
                onValueChange = state.input.onChange,
                singleLine = true,
                modifier = Modifier.testTag("text field")
            )

            Text(
                state.error,
                modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 10.dp),
                style = errorTextStyle()
            )


            SquaredTextButton("play", onClick = onStart, Modifier.testTag("Play").size(75.dp,50.dp))

            Spacer(Modifier.weight(1.0f))
        }
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    SquaredTheme {
        StartScreen(StartState(StartInput("Name",{}), StartColor(Color.Red,{}),"Hoed"),{}, {})
    }
}