package sel.group9.squared2.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow


class Settings(private val context: Context) {

    companion object {

        private lateinit var getLastLocation : () -> Task<Location>
        private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>

        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        fun setup(act: ComponentActivity) {
            sharedPreferences = act.getPreferences(Context.MODE_PRIVATE)
            editor = sharedPreferences!!.edit()

            requestPermissionLauncher = act.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { }

            if (
                ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            getLastLocation = {LocationServices.getFusedLocationProviderClient(act).lastLocation}
        }
    }


    private val _color = MutableStateFlow(sharedPreferences!!.getInt("Squared.color", 0))
    val playerColor : StateFlow<Int> = _color

    fun getSound():Float{
        return sharedPreferences!!.getFloat("Squared.Sound",0.5f)
    }
    fun getMusic():Float{
        return sharedPreferences!!.getFloat("Squared.Music",0.5f)
    }

    fun setColor(new:Int){
        editor!!.putInt("Squared.color",new)
        editor!!.apply()
        _color.value=new
    }
    fun getColorIndex():Int{
        return sharedPreferences!!.getInt("Squared.color",0)
    }
    fun setSound(new:Float){
        editor!!.putFloat("Squared.Sound",new)
        editor!!.apply()
    }
    fun setMusic(new:Float){
        editor!!.putFloat("Squared.Music",new)
        editor!!.apply()
    }

    fun getLocation():Task<Location>?{
        if (
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return null
        }
        return getLastLocation()
    }

    fun getLocationFlow(millis:Long): Flow<Task<Location>>{
        return flow{
            while(true){
                delay(millis)
                emit(getLocation()!!)
            }
        }
    }

    fun getId():String?{
        return if(sharedPreferences!!.contains("Squared.UserId"))
            sharedPreferences!!.getString("Squared.UserId","")
        else
            null
    }

    fun setId(new:String){
        editor!!.putString("Squared.UserId",new)
        editor!!.apply()
    }

    fun getName():String{
        return sharedPreferences!!.getString("Squared.Name","Name")!!
    }
    fun setName(new:String){
        editor!!.putString("Squared.Name",new)
        editor!!.apply()
    }
}