package sel.group9.squared2.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

//classes that add abstraction
data class UserLocation(val lat: Double, val lon: Double)

data class UserInfo(val name: String,val loc: UserLocation, val color: Int)

//classes to parse json to
data class PointSchema(val _id: String,val type:String,val coordinates:List<Double>)

data class User(val _id:String,val nickname:String,val color:Int,val location:PointSchema,val lastLocationUpdate : Long)

data class Tile(val _id:String,val color:Int)

data class Square(val color:Int,val lon: Double,val lat: Double) {
    companion object {
        val size = 0.0001
    }
}

// everything needs to go in a try-catch block, because okhttp can throw timeoutexceptions
class Backend(test:Boolean=false) {

    //val url = "http://10.0.2.2:3000/api/"
    var url = "https://squared2.xyz/api/"
//    init{
//        if (!test)
//            url = "http://localhost:3000/api/"
//    }

    private fun wrongLocation(loc:UserLocation):Boolean{
        return loc.lat < -90 || loc.lat>90 || loc.lon< -180 || loc.lon > 180
    }
    suspend fun getUser(id:String):User?{
        return withContext(
            Dispatchers.IO) {
            try {
                val url =
                    (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id).build()
                val req = Request.Builder().url(url).get().build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute()
                Gson().fromJson(resp.body?.string(), User::class.java)
            }catch(e:Exception){
                return@withContext null
            }
        }
    }
    
    suspend fun deleteUser(id:String):Int?{
        return withContext(Dispatchers .IO) {
            try {
                val url =
                    (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id).build()
                val req = Request.Builder().url(url).delete().build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute()
                val text = resp.body?.string()
                Gson().fromJson(text, Int::class.java)
            }catch(e:Exception){
                return@withContext null
            }
        }
    }

    suspend fun postUser(info:UserInfo):String?{
        return withContext(
            Dispatchers.IO) {
            if(wrongLocation(info.loc))
                return@withContext null
            try {
                val url =
                    (url + "user").toHttpUrl().newBuilder().addQueryParameter("nickname", info.name)
                        .addQueryParameter("color", info.color.toString())
                        .addQueryParameter("longitude", info.loc.lon.toString())
                        .addQueryParameter("latitude", info.loc.lat.toString()).build()
                val req = Request.Builder().url(url).post("".toRequestBody()).build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute()
                Log.v("Squared2", "postUser: $resp")
                Log.v("Squared2", "postUser: ${url.toString()}")
                val text = resp.body?.string().toString()
                val id = Gson().fromJson(text, String::class.java)
                id
            }catch(e:Exception){
                return@withContext null
            }
        }

    }

    suspend fun patchUser(id:String,info:UserInfo):String?{
        return withContext(
            Dispatchers.IO) {
            if(wrongLocation(info.loc))
                return@withContext null
            var testString = "null"
            try{
                val test = (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id).build()
                val nullTest =
                    OkHttpClient.Builder().build().newCall(Request.Builder().url(test).get().build())
                        .execute()
                testString = nullTest.body!!.string()
            }catch(e:Exception){

            }
            if (testString == "null") {
                postUser(info)
            } else {
                try{

                    val builder = (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id)
                        .addQueryParameter("nickname", info.name)
                        .addQueryParameter("color", info.color.toString())
                        .addQueryParameter("longitude", info.loc.lon.toString())
                        .addQueryParameter("latitude", info.loc.lat.toString())
                    val url = builder.build()
                    val req = Request.Builder().url(url).patch("".toRequestBody()).build()
                    val resp = OkHttpClient.Builder().build().newCall(req).execute()
                    Log.v("Squared2", "patchUser: $resp")
                    Log.v("Squared2", "patchUser: ${url.toString()}")
                    Gson().fromJson(resp.body?.string().toString(), User::class.java)._id
                }catch(e:Exception){
                    return@withContext null
                }
            }
        }
    }
    suspend fun nearbyUsers(loc:UserLocation,dist:Double):List<User>?{
        return withContext(Dispatchers.IO){
            try {
                val url = (url + "nearbyusers").toHttpUrl().newBuilder()
                    .addQueryParameter("latitude", loc.lat.toString())
                    .addQueryParameter("longitude", loc.lon.toString())
                    .addQueryParameter("distance", dist.toString()).build()
                val req = Request.Builder().url(url).get().build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
                Gson().fromJson(resp, object : TypeToken<List<User>>() {}.type)
            }catch(e:Exception){
                val temp : List<User>?=null
                temp
            }
        }
    }

    suspend fun addTile(id:String,loc:UserLocation,color:Int):Tile? {
        return withContext(Dispatchers.IO) {
            try {
                val url = (url + "placesquare").toHttpUrl().newBuilder()
                    .addQueryParameter("latitude", loc.lat.toString())
                    .addQueryParameter("longitude", loc.lon.toString())
                    .addQueryParameter("id", id)
                    .addQueryParameter("color", color.toString()).build()
                val req = Request.Builder().url(url).post("".toRequestBody()).build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
                Log.v("Squared2", "addTile: $resp")
                Log.v("Squared2", "addTile: $url")
                Gson().fromJson(resp, Tile::class.java)
            }catch(e:Exception){
                null
            }
        }
    }

    suspend fun nearbyTiles(loc:UserLocation,dist:Double):List<Square>?{
        return withContext(Dispatchers.IO){
            try{
                val url = (url+"nearbysquares").toHttpUrl().newBuilder().addQueryParameter("latitude",loc.lat.toString())
                    .addQueryParameter("longitude",loc.lon.toString()).addQueryParameter("distance",dist.toString()).build()
                val req = Request.Builder().url(url).get().build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
                Gson().fromJson(resp, object : TypeToken<List<Square>>() {}.type)
            }catch(e:Exception){
                val temp : List<Square>?=null
                temp
            }
        }
    }
}


