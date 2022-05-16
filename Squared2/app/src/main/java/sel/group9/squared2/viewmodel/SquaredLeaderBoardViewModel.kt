package sel.group9.squared2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sel.group9.squared2.data.ColorScore
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import sel.group9.squared2.model.leaderboard.LeaderBoardSelection
import javax.inject.Inject

@HiltViewModel
class SquaredLeaderBoardViewModel @Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private var amountOfTopUsersToFetch = 50

    private val _topUsers: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var topUsers: StateFlow<List<User>> = _topUsers

    private val _colorScores: MutableStateFlow<List<ColorScore>> = MutableStateFlow(listOf())
    var colorScores: StateFlow<List<ColorScore>> = _colorScores

    private val _leaderBoardSelection: MutableStateFlow<LeaderBoardSelection> = MutableStateFlow(LeaderBoardSelection.GLOBAL)
    var leaderBoardSelection: StateFlow<LeaderBoardSelection> = _leaderBoardSelection

    init {
        initialiseNetworkRequests();
    }

    private fun initialiseNetworkRequests() {
        viewModelScope.launch {
            while (true) {
                updateTopUsers()
                updateColorScores()
                delay(5000)
            }
        }
    }

    private suspend fun updateTopUsers() {
        val topUsers = backend.getTopUsers(amountOfTopUsersToFetch)

        if (topUsers != null) {
            _topUsers.value = topUsers
        }
    }

    private suspend fun updateColorScores() {
        val colorScores = backend.getColorScores()

        if (colorScores != null) {
            _colorScores.value = colorScores.sortedByDescending { it.squaresCaptured }
        }
    }

    fun setLeaderBoardSelection(selection: LeaderBoardSelection) {
        _leaderBoardSelection.value = selection
    }
//    private suspend fun loadMoreUsers() {
//
//    }
}