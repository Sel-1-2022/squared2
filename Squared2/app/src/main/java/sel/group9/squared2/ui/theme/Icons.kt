package sel.group9.squared2.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.R

val iconSize = 50.dp

@Composable
fun toggleGridIcon() = Image(
    painter = painterResource(id = R.drawable.toggle_grid),
    contentDescription = "Toggle grid",
    Modifier.size(iconSize)
)

@Composable
fun centerMapIcon() = Image(
    painter = painterResource(id = R.drawable.center_map),
    contentDescription = "Center Map",
    modifier = Modifier
        .size(iconSize - 8.dp)
        .padding(4.dp, 4.dp)
)

@Composable
fun orientationMapIcon() = Image(
    painter = painterResource(id = R.drawable.orientation_map),
    contentDescription = "Orient Map",
    modifier = Modifier
        .size(iconSize - 8.dp)
        .padding(4.dp, 4.dp)
)

@Composable
@Preview
private fun CenterMapIconPreview() {
    orientationMapIcon()
}
