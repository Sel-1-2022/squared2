package sel.group9.squared2.viewmodel

import android.location.Location
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
import sel.group9.squared2.ui.theme.orange
import sel.group9.squared2.ui.theme.red
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class SquaredTitleViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel(){
    private val _input = MutableStateFlow(backend.getName())
    var input : StateFlow<String> = _input

    fun changeInput(new:String){
        val value = new.replace("\n","").replace("\r","")
        _input.value=value
        backend.setName(value)
    }

    fun color():StateFlow<Int>{
        return backend.getColor()
    }

    fun commit() {
        val id = backend.getId()
        backend.getLocation().addOnCompleteListener { loc ->
            viewModelScope.launch {
                if(id==null){
                    val newid = backend.postUser(
                        loc.result.latitude, loc.result.longitude
                    )
                    backend.setId(newid)
                }else{
                    backend.patchUser(lat=loc.result.latitude,long=loc.result.longitude)
                }
            }
        }
    }
}

