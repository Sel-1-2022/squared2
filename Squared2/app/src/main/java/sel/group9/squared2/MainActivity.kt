package sel.group9.squared2

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import sel.group9.squared2.data.Settings
import sel.group9.squared2.ui.navigation.SquaredNavGraph
import sel.group9.squared2.ui.theme.SquaredTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private lateinit var fusedLocationClient: FusedLocationProviderClient

  companion object{
    var position = 0

    var audio :Float? = null
    var effect: Float? = null

    var player:MediaPlayer? = null
    var soundPlayer: MediaPlayer? = null

    fun changeAudio(new:Float){
      audio=new
      player!!.setVolume(new,new)
    }

    fun changeEffect(new:Float){
      effect=new
      soundPlayer?.setVolume(new,new)
    }

    fun playEffect(){
      soundPlayer?.start()
    }

    fun startAudio(act:MainActivity, music:Float, sound:Float){
      if(player==null) {
        audio=music
        player = MediaPlayer.create(act, R.raw.backgroundsound)
        player!!.isLooping = true // Set looping
        player!!.setVolume(music, music)
        player!!.seekTo(position)
        player!!.start()
      }
      if(soundPlayer==null){
        effect=sound
        soundPlayer = MediaPlayer.create(act,R.raw.buttonsound)
        soundPlayer?.setVolume(sound,sound)
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    Log.v("LocationTest", "here")
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
//      Log.v("LocationTest", "permission not provided")
      return
    }
//    fusedLocationClient
//      .lastLocation
//      .addOnSuccessListener {
//        location -> Log.v("LocationTest", location.toString())
//      }
    //loads in the settings and the music
    if(audio!=null){
      startAudio(this,audio!!,effect!!)
    }else{
      Settings.setup(this)
    }

    setContent {
      SquaredTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          SquaredNavGraph(this, fusedLocationClient);
        }
      }
    }
  }


  override fun onResume() {
    if(player!=null && !player!!.isPlaying){
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
      player=null
    }
    if(soundPlayer!=null){
      soundPlayer!!.stop()
      soundPlayer!!.release()
      soundPlayer=null
    }
    super.onDestroy()
  }
}

