package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.data.PointSchema
import sel.group9.squared2.data.User
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun LeaderBoardUserList(self:User?,users: List<User>, scrollState: ScrollState) {
    Column(
        Modifier
            .height(256.dp)
            .verticalScroll(scrollState)
    ) {
        LeaderBoardUserListItem(
            ranking = null,
            name = self?.nickname ?: "loading",
            score = self?.squaresCaptured ?: 0
        )
        Text("-")
        users.forEachIndexed {
            index, user -> LeaderBoardUserListItem(
                ranking = (index+1).toLong(),
                name = user.nickname,
                score = user.squaresCaptured
            )
        }
    }
}

@Composable
@Preview
private fun LeaderBoardUserListPreview() {
    val scrollState = rememberScrollState()
    SquaredTheme {
        LeaderBoardUserList(User("1", "Hoed", 1, PointSchema("", "square", listOf(0.0, 0.0)),29, 10)
                ,listOf(User("1", "Hoed", 1, PointSchema("", "square", listOf(0.0, 0.0)),29, 10)
        ), scrollState = scrollState)
    }
}