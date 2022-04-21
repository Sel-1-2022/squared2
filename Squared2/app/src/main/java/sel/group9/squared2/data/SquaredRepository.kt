package sel.group9.squared2.data

import android.location.Location
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(private val backend: Backend, private val settings: Settings) {

    suspend fun postUser(lat:Double,long:Double):String{
        return backend.postUser(settings.getName(),settings.getColorIndex(),lat,long)
    }

    suspend fun getUser(): User{
        return backend.getUser(settings.getId()!!)
    }


    suspend fun patchUser(lat:Double?=null,long:Double?=null,last:Int?=null):User{
        return backend.patchUser(settings.getId()!!,settings.getName(),settings.getColorIndex(),lat,long,last)
    }

    suspend fun nearbyUser(lat:Double,long:Double,dist:Double):List<User>{
        Log.v("test","here!")
        val a =  backend.nearbyUsers(lat,long,dist)
        Log.v("test","here!!")

        return a
    }
    suspend fun placeTile(lat:Double,long:Double){
        backend.addTile(settings.getId()!!,lat,long,settings.getColorIndex())
    }

    suspend fun nearbyTiles(lat:Double,long:Double,dist:Double):List<Square>{
        return backend.nearbyTiles(lat,long,dist)
    }

    fun getLocation(): Task<Location>{
        return settings.getLocation()
    }
    fun getLocationFlow(millis:Long): Flow<Task<Location>>{
        return settings.getLocationFlow(millis)
    }

    fun getColor():StateFlow<Int>{
        return settings.playerColor
    }
    fun setColor(new:Int){
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
    fun getId():String?{
        return settings.getId()
    }
    fun setId(id:String){
        settings.setId(id)
    }
    fun getName():String{
        return settings.getName()
    }
    fun setName(name:String){
        settings.setName(name)
    }

}
