package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sel.group9.squared2.data.ColorScore

private fun getColorRowList(colorScores: List<ColorScore>): ArrayList<ArrayList<ColorScore>> {
    val rows = ArrayList<ArrayList<ColorScore>>()
    if (colorScores.isNotEmpty()) rows.add(ArrayList())

    val iterator = colorScores.iterator()
    while (iterator.hasNext()) {
        var currentRow = rows[rows.size - 1]
        if (currentRow.size >= 4) {
            currentRow = ArrayList()
            rows.add(currentRow)
        }

        currentRow.add(iterator.next())
    }

    return rows
}

@Composable
fun LeaderBoardColorList(colorScores: List<ColorScore>) {
    val rows = getColorRowList(colorScores)

    Column {
        rows.forEachIndexed { index, row ->
            Row {
                row.forEachIndexed { index, color ->
                    LeaderBoardColorListItem(color = color.color, score = color.squaresCaptured)
                    if (index != 3) {
                        Spacer(modifier = Modifier.weight(1.0f))
                    }
                }
            }
            if (index != rows.lastIndex) {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

}