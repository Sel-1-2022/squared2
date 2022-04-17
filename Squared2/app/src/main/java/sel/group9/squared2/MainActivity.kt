package sel.group9.squared2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier

import dagger.hilt.android.AndroidEntryPoint
import sel.group9.squared2.ui.navigation.SquaredNavGraph

import sel.group9.squared2.ui.theme.SquaredTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SquaredTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          SquaredNavGraph();
        }
      }
    }
  }

}

