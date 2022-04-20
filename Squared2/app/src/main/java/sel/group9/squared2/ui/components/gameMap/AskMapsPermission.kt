package sel.group9.squared2.ui.components.gameMap

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun AskMapsPermission(content: @Composable () -> Unit) {
    // location permission state
    val locationPermissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale by rememberSaveable { mutableStateOf(false) }

    PermissionRequired(
        permissionState = locationPermissionState,
        permissionNotGrantedContent = {
            if (doNotShowRationale) {
                Text("Feature not available")
            } else {
                Column {
                    Text("Location is needed for this app. Please grant the permission.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                            Text("Ok!")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { doNotShowRationale = true }) {
                            Text("Nope")
                        }
                    }
                }
            }
        },
        permissionNotAvailableContent = {
            Column {
                Text(
                    "Location permission denied. Please, grant us access on the Settings screen."
                )
            }
        }
    ) {
        content()
    }
}