package sel.group9.squared2.data

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.compose.ui.graphics.Color
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import sel.group9.squared2.MainActivity
import sel.group9.squared2.ui.theme.red

class Settings {
    companion object {
        private var changeAudio : (Float)->Unit = {}
        private var startAudio: (Float,Float)->Unit = { _, _ ->  }
        private var changeEffect : (Float)->Unit = {}
        private var location : (()->Task<Location>)? = null

        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        fun setup(act: MainActivity) {
            changeAudio = {x-> MainActivity.changeAudio(x)}
            startAudio = {x,y->MainActivity.startAudio(act,x,y)}
            changeEffect = {x-> MainActivity.changeEffect(x)}
            sharedPreferences = act.getPreferences(Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            location={act.getLocation()!!}
        }
    }

    private val _color = MutableStateFlow(Color(sharedPreferences!!.getLong("Squared.PlayerColor", red.value.toLong()).toULong()))
    val playerColor : StateFlow<Color> = _color

    init{
        startAudio(this.getMusic(),this.getSound())
    }

    fun getSound():Float{
        return sharedPreferences!!.getFloat("Squared.Sound",0.5f)
    }
    fun getMusic():Float{
        return sharedPreferences!!.getFloat("Squared.Music",0.5f)
    }
    fun getColor(): StateFlow<Color> {
        return playerColor
    }

    fun setColor(new:Color){
        editor!!.putLong("Squared.PlayerColor",new.value.toLong())
        editor!!.apply()
        _color.value=new
    }

    fun setSound(new:Float){
        editor!!.putFloat("Squared.Sound",new)
        editor!!.apply()
        changeEffect(new)
    }
    fun setMusic(new:Float){
        editor!!.putFloat("Squared.Music",new)
        editor!!.apply()
        changeAudio(new)
    }

    fun getLocation():Task<Location>{
        return location!!()
    }

    fun getLocationFlow(millis:Long): Flow<Task<Location>>{
        return flow{
            while(true){
                delay(millis)
                emit(getLocation())
            }
        }
    }
}