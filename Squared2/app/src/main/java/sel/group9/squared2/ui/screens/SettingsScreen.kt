package sel.group9.squared2.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.MainActivity
import sel.group9.squared2.R
import sel.group9.squared2.viewmodel.SquaredSettingsViewModel
import sel.group9.squared2.ui.components.SquaredSlider
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SettingsRoute(activity:MainActivity,model: SquaredSettingsViewModel, onBack:()->Unit){
    model.startPlayer(activity)
    val onReturn : ()->Unit = model.getBack{onBack()}

    SettingsScreen(
        sound = model.sound.collectAsState().value,
        music = model.music.collectAsState().value,
        onSoundChange = {x->
            model.setSound(x)
            model.beepSound(x)
                        },
        onMusicChange = {x->
            model.setMusic(x)
            model.beepSound(x)
        },
        onCancel = {onReturn()}
    ) {
        model.confirmChanges()
        onReturn()
    }
}


@Composable
fun SettingsScreen(sound:Float,music:Float,onSoundChange:(Float)->Unit,
                   onMusicChange:(Float)->Unit,onCancel:()->Unit,onAccept:()->Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1.0f))

        Text("music volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = music,
            onValueChange = onMusicChange,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text("sound volume", style = MaterialTheme.typography.h2)
        SquaredSlider(
            value = sound,
            onValueChange = onSoundChange,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.weight(1.0f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            Spacer(Modifier.weight(0.5f))

            SquaredTextButton("cancel", onClick = onCancel)

            Spacer(Modifier.weight(0.2f))

            SquaredTextButton("okay", onClick = onAccept)
            Spacer(Modifier.weight(0.5f))
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
@Preview
private fun SettingsScreenPreview() {
    SquaredTheme {
        SettingsScreen(0.5f,0.5f,{},{},{},{})
    }
}
