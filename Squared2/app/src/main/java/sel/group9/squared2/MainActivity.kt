package sel.group9.squared2

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import sel.group9.squared2.data.Settings
import sel.group9.squared2.ui.navigation.SquaredNavGraph
import sel.group9.squared2.ui.theme.SquaredTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  companion object{
    var position = 0
  }
  var player:MediaPlayer? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    //loads in the settings
    Settings.setup(this)

    setContent {
      SquaredTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          SquaredNavGraph();
        }
      }
    }
  }

  fun changeAudio(audio:Float){
    player!!.setVolume(audio,audio)
  }
  fun startAudio(audio:Float){
    if(player==null) {
      player = MediaPlayer.create(this, R.raw.backgroundsound)
      player!!.isLooping = true // Set looping
      player!!.setVolume(audio, audio)
      player!!.seekTo(position)
      player!!.start()
    }
  }


  override fun onResume() {
    if(player!=null){
      player!!.start()
    }
    super.onResume()
  }

  override fun onPause() {
    if(player!=null){
      player!!.pause()
    }
    super.onPause()
  }

  override fun onDestroy() {
    if(player!=null){
      position=player!!.currentPosition
      player!!.stop()
      player!!.release()
    }
    super.onDestroy()
  }
}

