package com.devmax.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.devmax.profile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class WeatherFragment : Fragment() {

    data class weatherData (val temp: Double, val description : String, val condition: Int)
    data class locationData (val city: String, val district: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather, container, false)


        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation(view)
        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }


        return view
    }

    private fun getIconWeather (condition: Int): Int {
        return when (condition) {
            800 -> R.drawable.sunny_24px
            1003 -> R.drawable.partly_cloudy_day_24px
            802 -> R.drawable.cloud_24px
            1183 -> R.drawable.air_24px
            in 200..232 -> R.drawable.thunderstorm_24px
            in 300..331 -> R.drawable.thunderstorm_24px
            in 500..531 -> R.drawable.rainy_24px
            in 600..612 -> R.drawable.snowing_24px
            in 701..781 -> R.drawable.foggy_24px

            else -> R.drawable.sunny_snowing_24px
        }
    }
    private fun getColorWeather (condition: Int): String {
        return when (condition) {
            800 -> "#FBC740"
            801 -> "#BCECE0"
            802 -> "#BCECE0"
            1183 -> "#14C2DD"
            in 200..232 -> "#637E90"
            in 300..331 -> "#29B3FF"
            in 500..531 -> "#14C2DD"
            in 600..612 -> "#E5F2F0"
            in 701..781 -> "#FFFEA8"
            else -> "#FBC740"
        }
    }
    private fun upImage(weatherData: weatherData, cityData: locationData,view: View){
        val temperatura = view.findViewById<TextView>(R.id.textWTemperaure)
        val city = view.findViewById<TextView>(R.id.textWLocation)
        val country = view.findViewById<TextView>(R.id.textWcountry)
        val imageWather = view.findViewById<ImageView>(R.id.imageWather)
        val state = view.findViewById<TextView>(R.id.textWstate)
        val icon = getIconWeather(weatherData.condition)
        val iconColor = getColorWeather (weatherData.condition)

        try {
            temperatura.text="${weatherData.temp}°C"
            state.text="${weatherData.description}"
            city.text= "${cityData.city}"
            country.text="${cityData.district}"

                    //dou o valor da chave, escolho onde buscar (drawable) e chamo com um require
            val drawableRef = resources.getIdentifier(icon.toString(), "drawable", requireContext().packageName)

            imageWather.setImageResource(drawableRef)

            val color = Color.parseColor(iconColor)
            imageWather.setColorFilter(color)
        }catch (e: Exception){

            e.printStackTrace()
        }
    }

    //dados do clima
    private suspend fun getDataWeather(latitude: Double, longitude: Double): weatherData {
        val apiKey = "a159e1cfbd724e04aef173513251104"
        val url="https://api.weatherapi.com/v1/current.json?lang=pt&key=$apiKey&q=$latitude,$longitude&aqi=no"
        try {
            val jsonText = withContext(Dispatchers.IO) {
                URL(url).readText()
            }
            val jsonObject= JSONObject(jsonText)
            val current = jsonObject.getJSONObject("current")
            val temp = current.getDouble("temp_c")
            val descrip = current.getJSONObject("condition").getString("text")
            val condit = current.getJSONObject("condition").getInt("code")


            return weatherData(temp, descrip, condit)

        } catch (e: Exception){
            Log.e("Falha ao GERAR O JASON", e.message.toString())
            e.printStackTrace()
            return weatherData(0.0, "Nublado", 1003)
        }

    }
//
    //dados da localização
    private suspend fun getCity(latitude: Double, longitude: Double): locationData {
        try {
            val apiKey = "bdc_93ee15cab2cf4fd9a6babdbe9a34f341"
            val url = "https://api-bdc.net/data/reverse-geocode?latitude=$latitude&longitude=$longitude&localityLanguage=pt&key=$apiKey"
            val jsonText = withContext(Dispatchers.IO) {
                URL(url).readText()
            }
            val jsonObject = JSONObject(jsonText)
            val cit = jsonObject.getString("city")
            val distr = jsonObject.getString("countryName")



            return locationData(cit, distr)
        }catch (e: Exception){
            Log.e("WatherFragment >> getCityDistrict", e.message.toString())
            e.printStackTrace()
            return locationData("","")
        }
    }

    private fun getLocation (view: View){
        val locationManeger = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            return
        }

        locationManeger.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f, object: LocationListener{
            override fun onLocationChanged(location: Location) {
                lifecycleScope.launch {
                    val weatheData = getDataWeather(location.latitude, location.longitude)

                    val cityData = getCity(location.latitude, location.longitude)

                        upImage(weatheData, cityData, view)


                }

            }

        })
    }

}