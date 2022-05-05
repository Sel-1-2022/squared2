package sel.group9.squared2.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun SquaredSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    colors: SliderColors = SliderDefaults.colors()
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
//        interactionSource = interactionSource,
//        colors = colors
    )
}

@Composable
@Preview
private fun SquaredSliderPreview() {
    SquaredTheme {
        SquaredSlider(value = 0.5f, onValueChange = {})
    }
}