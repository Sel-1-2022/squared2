package sel.group9.squared2.ui.components.colorSelection

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.data.Settings
import sel.group9.squared2.ui.theme.*

private fun getColorRowList(rowLength: Int = 3): ArrayList<ArrayList<Color>> {
    val rows = ArrayList<ArrayList<Color>>()
    if (colorList.size > 0) rows.add(ArrayList())

    val iterator = colorList.iterator()
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
fun ColorSelectionGrid(selected:Color,onClick:(Int)->Unit, rowLength: Int = 3) {
    val rows = getColorRowList(rowLength)
    var index = 0
    Column {
        rows.forEach {
            row ->
            Row {
                row.forEach { color ->
                    var temp = index
                    ColorSelection(
                        color = color,
                        selected = selected == color,
                        modifier = Modifier.padding(15.dp).testTag("ColorSelectionBox"),
                        onClick = {
                            Log.v("test",index.toString())
                            onClick(temp)
                        }
                    )
                    Log.v("test",index.toString())
                    index++
                }
            }
        }
    }
}

@Composable
@Preview
private fun ColorSelectionListPreview() {
    SquaredTheme {
        ColorSelectionGrid(red,{})
    }
}