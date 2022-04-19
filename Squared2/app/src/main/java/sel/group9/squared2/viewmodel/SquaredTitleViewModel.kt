package sel.group9.squared2.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredTitleViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel(){
    fun color():StateFlow<Color>{
        return backend.getColor()
    }
}

