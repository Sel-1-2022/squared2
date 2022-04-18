package sel.group9.squared2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.viewmodel.SquaredColorViewModel
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.components.colorSelection.ColorSelectionGrid
import sel.group9.squared2.ui.theme.*

@Composable
fun ColorSelectionRoute(model: SquaredColorViewModel, onBack:()->Unit){
    ColorSelectionScreen(selected = model.getColor().collectAsState().value,onBack = onBack,onSelect = {x->model.setColor(x)})
}

@Composable
fun ColorSelectionScreen(selected:Color,onBack:()->Unit,onSelect:(Color)->Unit) {
    val colors = listOf(
        red, orange, warmYellow,
        yellowGreen, limeGreen, coldGreen,
        lightBlue, blue, darkBlue,
        purple, coldPink, warmPink
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        Text("color picker", style = MaterialTheme.typography.h2)
        ColorSelectionGrid(colors = colors,selected=selected,onClick = onSelect)

        Spacer(modifier = Modifier.weight(1.0f))

        SquaredTextButton(text = "okay", onClick = onBack)

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
@Preview
private fun ColorSelectionScreenPreview() {
    val colors = listOf(
    red, orange, warmYellow,
    yellowGreen, limeGreen, coldGreen,
    lightBlue, blue, darkBlue,
    purple, coldPink, warmPink
    )
    SquaredTheme {
        ColorSelectionScreen(colors.first(),{},{})
    }
}
