package sel.group9.squared2.ui.components.colorSelector

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.ui.theme.*

private fun getColorRowList(colors: Collection<Color>, rowLength: Int = 3): ArrayList<ArrayList<Color>> {
    val rows = ArrayList<ArrayList<Color>>()
    if (colors.size > 0) rows.add(ArrayList())

    val iterator = colors.iterator()
    while (iterator.hasNext()) {
        var currentRow = rows.get(rows.size - 1)
        if (currentRow.size >= rowLength) {
            currentRow = ArrayList()
            rows.add(currentRow)
        }

        currentRow.add(iterator.next())
    }

    return rows
}

@Composable
fun ColorSelectionList(colors: Collection<Color>, rowLength: Int = 3) {
    val rows = getColorRowList(colors, rowLength)
    val currentSelection = colors.first()

    Column {
        rows.forEach {
            row ->
            Row {
                row.forEach { color ->
                    ColorSelection(
                        color = color,
                        selected = currentSelection == color,
                        modifier = Modifier.padding(15.dp),
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ColorSelectionListPreview() {
    val colors = listOf(
        red, orange, warmYellow,
        yellowGreen, limeGreen, coldGreen,
        lightBlue, blue, darkBlue,
        purple, coldPink, warmPink
    )

    SquaredTheme {
        ColorSelectionList(colors = colors)
    }
}