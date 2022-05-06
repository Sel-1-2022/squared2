package sel.group9.squared2.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.MainActivity
import sel.group9.squared2.R
import sel.group9.squared2.viewmodel.SquaredSettingsViewModel
import sel.group9.squared2.ui.components.SquaredSlider
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SettingsRoute(model: SquaredSettingsViewModel, onBack:()->Unit){

    SettingsScreen(
        SoundSettings(
            music = ChangeableValue(
                value = model.music.collectAsState().value,
                onChange = {x->
                    model.setMusic(x)
                    model.beepSound(x)
                }
        ),
            sound = ChangeableValue(
                value = model.sound.collectAsState().value,
                onChange = {x->
                    model.setSound(x)
                    model.beepSound(x)
                }
                )
        ),
        onCancel = onBack
    ) {
        model.confirmChanges()
        onBack()
    }
}

data class ChangeableValue<T>(val value: T,val onChange:(T)->Unit)

data class SoundSettings(val music : ChangeableValue<Float>, val sound : ChangeableValue<Float>)

@Composable
fun SettingsScreen(settings: SoundSettings,onCancel:()->Unit,onAccept:()->Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1.0f))

        Text("music volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = settings.music.value,
            onValueChange = settings.music.onChange,
            modifier = Modifier.fillMaxWidth(0.8f).testTag("MusicSlider")
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text("sound volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = settings.sound.value,
            onValueChange = settings.sound.onChange,
            modifier = Modifier.fillMaxWidth(0.8f).testTag("SoundSlider")
        )

        Spacer(Modifier.weight(1.0f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            Spacer(Modifier.weight(0.5f))

            SquaredTextButton("cancel", onClick = onCancel, Modifier.testTag("Back"))

            Spacer(Modifier.weight(0.2f))

            SquaredTextButton("okay", onClick = onAccept, Modifier.testTag("Okay"))
            Spacer(Modifier.weight(0.5f))
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
@Preview
private fun SettingsScreenPreview() {
    SquaredTheme {
        SettingsScreen(SoundSettings(ChangeableValue(0.5f,{}),ChangeableValue(0.5f,{})),{},{})
    }
}
