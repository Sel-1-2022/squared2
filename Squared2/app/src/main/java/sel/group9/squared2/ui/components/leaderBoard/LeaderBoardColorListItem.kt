package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ColorSelection
import sel.group9.squared2.sound.ButtonBasics
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.borderWidth
import sel.group9.squared2.ui.theme.colorList
import sel.group9.squared2.ui.theme.iconSize

@Composable
fun LeaderBoardColorListItem(color: Int, score: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ColorSelection(basic = ButtonBasics({}, Modifier.size(iconSize + borderWidth * 2)), color = colorList[color])
        Text(text = "$score", textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
private fun LeaderBoardColorListItemPreview() {
    SquaredTheme {
        LeaderBoardColorListItem(0, 100)
    }
}