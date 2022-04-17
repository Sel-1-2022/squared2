package sel.group9.squared2.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredTitleViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel(){
    val color = backend.getColor()
}

