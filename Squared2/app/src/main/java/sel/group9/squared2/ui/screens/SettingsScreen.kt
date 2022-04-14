package sel.group9.squared2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sel.group9.squared2.ui.components.SquaredButton
import sel.group9.squared2.ui.components.SquaredSlider
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SettingsScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1.0f))
        Text("music volume",
            fontSize = 48.sp
        )
        SquaredSlider(
            value = 0.5f,
            onValueChange = {},
            modifier = Modifier
                .width(350.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text("sound volume",
            fontSize = 48.sp
        )
        SquaredSlider(value = 0.7f, onValueChange = {},
            modifier = Modifier
                .width(350.dp)
        )

        Spacer(Modifier.weight(1.0f))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            SquaredButton(onClick = { }) {
                Text("cancel")
            }

            Spacer(modifier = Modifier.width(30.dp))
            
            SquaredButton(onClick = { }) {
                Text("okay")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@Preview
private fun SettingsScreenPreview() {
    SquaredTheme {
        SettingsScreen()
    }
}
