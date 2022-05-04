package sel.group9.squared2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import sel.group9.squared2.data.Settings
import sel.group9.squared2.ui.components.gameMap.AskLocationPermissions
import sel.group9.squared2.ui.navigation.SquaredNavGraph
import sel.group9.squared2.ui.theme.SquaredTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

}

