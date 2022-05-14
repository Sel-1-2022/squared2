package sel.group9.squared2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import sel.group9.squared2.data.Settings
import sel.group9.squared2.data.SoundManager
import sel.group9.squared2.ui.navigation.SquaredNavGraph
import sel.group9.squared2.ui.theme.SquaredTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //loads in the settings
    Settings.setup(this)

    //loads in the sound
    SoundManager.setup(this)

    setContent {
      SquaredTheme {
          Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            SquaredNavGraph();
        }
      }
    }
  }

}

