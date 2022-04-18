package sel.group9.squared2.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import sel.group9.squared2.MainActivity

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
        }
    }

    init{
        startAudio(this.getMusic())
    }

    fun getSound():Float{
        return sharedPreferences!!.getFloat("Squared.Sound",0.5f)
    }
    fun getMusic():Float{
        return sharedPreferences!!.getFloat("Squared.Music",0.5f)
    }
    fun getColor(): Color {
        return Color(sharedPreferences!!.getLong("Squared.PlayerColor",0xFFFF0000))
    }

    fun setColor(new:Color){
        editor!!.putLong("Squared.PlayerColor",new.value.toLong())
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