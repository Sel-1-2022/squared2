package sel.group9.squared2.ui.components.gameMap

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle

@Composable
fun UserDot(latLng: LatLng, color: Color) {
    Box {
        Circle(center = latLng, radius = 0.00001, fillColor = color, strokeColor = Color.Black, strokeWidth = 0.000003f)
    }
}

@Composable
@Preview
fun UserDotPreview() {

}