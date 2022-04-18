package sel.group9.squared2.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sel.group9.squared2.MainActivity
import sel.group9.squared2.ui.theme.orange
import sel.group9.squared2.ui.theme.red

class Settings {
    companion object {
        private var changeAudio : (Float)->Unit = {}
        private var startAudio: (Float)->Unit = {}
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        fun setup(act: MainActivity) {
            changeAudio = {x-> MainActivity.changeAudio(x)}
            startAudio = {x->MainActivity.startAudio(act,x)}
            sharedPreferences = act.getPreferences(Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()
            Log.v("test",red.value.toString())
            Log.v("test",sharedPreferences!!.getLong("Squared.PlayerColor", red.value.toLong()).toULong().toString())
        }
    }

    private val _color = MutableStateFlow(Color(sharedPreferences!!.getLong("Squared.PlayerColor", red.value.toLong()).toULong()))
    val playerColor : StateFlow<Color> = _color

    init{
        startAudio(this.getMusic())
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
        changeAudio(new)
    }
    fun setMusic(new:Float){
        editor!!.putFloat("Squared.Music",new)
        editor!!.apply()
        changeAudio(new)
    }
}