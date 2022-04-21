package sel.group9.squared2.ui.components.gameMap

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle

@Composable
fun UserDot(latLng: LatLng, color: Color) {
    Circle(center = latLng, radius = .6, fillColor = color, zIndex = 1.0f, strokeWidth = 0.0f)
    Circle(center = latLng, radius = 1.4, fillColor = Color(color.red, color.green, color.blue, 0.5f), zIndex = 0.5f, strokeWidth = 0.0f)

}

@Composable
@Preview
fun UserDotPreview() {

}