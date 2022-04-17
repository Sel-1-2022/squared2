package sel.group9.squared2

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SquaredViewModel@Inject constructor(
    private val backend: SquaredRepository ) : ViewModel(){

    val test = backend.getTest()
}

