package sel.group9.squared2.data

import android.util.Log
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(private val backend: Backend, private val settings: Settings) {

    suspend fun postUser(name:String,color:String,lat:Int,long:Int):String{
        return backend.postUser(name,color,lat,long)
    }

    suspend fun getUser(id:String): User{
        return backend.getUser(id)
    }

    suspend fun patchUser(id:String,name:String?=null,color:String?=null,lat:Int?=null,long:Int?=null,last:Int?=null):User{
        return backend.patchUser(id,name,color,lat,long,last)
    }

    suspend fun nearbyUser(lat:Int,long:Int,dist:Int):List<User>{
        return backend.nearbyUsers(lat,long,dist)
    }

    fun getColor():StateFlow<Color>{
        return settings.getColor()
    }
    fun setColor(new:Color){
        settings.setColor(new)
    }
    fun getSound():Float{
        return settings.getSound()
    }
    fun getMusic():Float{
        return settings.getMusic()
    }
    fun setSound(new:Float){
        settings.setSound(new)
    }
    fun setMusic(new:Float){
        settings.setMusic(new)
    }
}
