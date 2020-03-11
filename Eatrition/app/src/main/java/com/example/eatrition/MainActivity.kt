package com.example.eatrition

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import kotlin.reflect.typeOf

private const val PERMISSION_REQUEST = 10
class MainActivity : AppCompatActivity() {

    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var locationGps: Location? = null

    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.setBackgroundColor(Color.GRAY)

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = MainAdapter()
        getLocation()


        //IF STATEMENTS BASED ON USER PERONALIZATION... set finQuery
        var finQuery:String
        finQuery = "Sugar"

        //IF STATEMENTS BASED ON USER PERONALIZATION... set finQuery



        println("MY LSLSLSLLLSLSLLS" + finQuery)
        fetchYelp(locationGps!!.latitude.toDouble(), locationGps!!.longitude.toDouble(), finQuery)
    }

    fun fetchYelp(lat : Double, long: Double, query : String){
        println("Starting API Attempt")
        val limit = "limit=3" //add this to the end of the final query
        val url = "https://api.yelp.com/v3/businesses/search?"

        //AT LONG and QUERY SET UP
        val fLat = "latitude="+lat.toString()
        val fLong = "longitude="+long.toString()
        val fQuery = "term="+query

        //COMBINE LAT LONG AND QUERY TO FINAL URL
        val finalurl = url + fLat + "&"+ fLong + "&" + fQuery + "&" + limit

        println(finalurl)
        //AUTHORIZATION HEADER CREATION
        val Api_Key = "XXXXX"
        val mix = "Bearer " + Api_Key


        val request = Request.Builder().header("Authorization",mix).url(finalurl).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)

                //Create Json reader and read into the yelpfeed class
                val gson = GsonBuilder().create()
                val yelpFeed = gson.fromJson(body,YelpFeed::class.java)

                //Create the UI from info from json
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(yelpFeed)}
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (hasGps){
            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
                    LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location
                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null) {
                    locationGps = localGpsLocation
                }
            }

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}


class YelpFeed(val businesses: List<Business>)

class Business(val id: String, val name: String, val image_url: String)