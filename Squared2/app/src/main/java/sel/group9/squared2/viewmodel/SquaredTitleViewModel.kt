package sel.group9.squared2.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.toHexString
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.UserLocation
import sel.group9.squared2.ui.theme.orange
import sel.group9.squared2.ui.theme.red
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class SquaredTitleViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel(){
    private val _input = MutableStateFlow(backend.getName())
    var input : StateFlow<String> = _input

    private val _error = MutableStateFlow("")
    var error : StateFlow<String> = _error

    fun changeInput(new:String){
        val value = new.replace("\n","").replace("\r","")
        _input.value=value
        backend.setName(value)
    }

    fun color():StateFlow<Int>{
        return backend.getColor()
    }

    fun commit(onClick:()->Unit) {

        val id = backend.getId()
        backend.getLocation().addOnCompleteListener { loc ->
                if(id==null){
                    tryCommit({backend.postUser(
                        UserLocation(loc.result.latitude, loc.result.longitude))
                    },onClick)
                }else{
                    tryCommit({backend.patchUser(UserLocation(lat =loc.result.latitude, lon =loc.result.longitude))},onClick)
                }
        }
    }


    private fun tryCommit(toTry:suspend ()->String?, onClick:()->Unit){
        viewModelScope.launch {
            val first = toTry()
            if (first == null) {
                val second = toTry()
                if (second == null) {
                    _error.value = "Something went wrong. Try again"
                } else {
                    onClick()
                }
            } else {
                onClick()
            }
        }
    }
}

