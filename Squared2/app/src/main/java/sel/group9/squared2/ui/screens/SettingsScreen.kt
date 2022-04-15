package sel.group9.squared2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.components.SquaredSlider
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SettingsScreen() {
    val musicVolume = 0.5f
    val soundVolume = 0.7f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1.0f))

        Text("music volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = musicVolume,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text("sound volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = soundVolume,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.weight(1.0f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            Spacer(Modifier.weight(0.5f))

            SquaredTextButton("cancel", onClick = { })

            Spacer(Modifier.weight(0.2f))

            SquaredTextButton("okay", onClick = { })
            Spacer(Modifier.weight(0.5f))
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
@Preview
private fun SettingsScreenPreview() {
    SquaredTheme {
        SettingsScreen()
    }
}
