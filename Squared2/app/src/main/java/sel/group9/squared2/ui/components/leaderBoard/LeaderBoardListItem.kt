package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun LeaderBoardUserListItem(ranking: Long?, name: String, score: Long) {
    var rankingString="xxxxx. ";
    if(ranking!=null){
        rankingString="" + ranking
        rankingString = "0".repeat(5 - rankingString.length) + rankingString + ". "
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(rankingString)
        Text(name)
        Text(score.toString(),
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun LeaderBoardListItemPreview() {
    SquaredTheme {
        LeaderBoardUserListItem(ranking = 1, name = "hat", score = 20)
    }
}