package sel.group9.squared2.ui.screens

import androidx.compose.material.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.ui.components.SquaredButton
import sel.group9.squared2.ui.components.SquaredTextField
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun StartScreen() {
    val currentColor = Color.Red
    val currentName = "name"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Row (
          modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.weight(1.0f))
            SquaredButton(onClick = { }) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
            Spacer(Modifier.width(30.dp))
        }
        Spacer(Modifier.weight(0.8f))
        Text(
            text = "squared²",
            fontSize = 72.sp
        )

        Spacer(Modifier.height(60.dp))

        ColorSelection(
            color = currentColor,
            selected = true
        )

        Spacer(Modifier.height(20.dp))

        SquaredTextField(value = "hoed", onValueChange = {})

        Spacer(Modifier.height(60.dp))

        SquaredButton(onClick = {}) {
            Text("play")
        }

        Spacer(Modifier.weight(1.0f))
    }
}

@Composable
@Preview
private fun StartScreenPreview() {
    SquaredTheme {
        StartScreen()
    }
}