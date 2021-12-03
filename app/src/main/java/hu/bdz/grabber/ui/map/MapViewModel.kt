package hu.bdz.grabber.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import hu.bdz.grabber.GrabberApplication
import hu.bdz.grabber.model.Place
import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import hu.bdz.grabber.repository.PlaceRepository
import hu.bdz.grabber.retrofit.NearbySearchAPI
import hu.bdz.grabber.service.LocationService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MapViewModel : ViewModel() {

    private val placeRepository: PlaceRepository

    var allLivePlaces: LiveData<List<Place>>
    var allCachedPlaces = mutableListOf<Place>()

    var placeCount: Int = 0

    var returnedPlace: Place? = null

    var cacheUpdated = MutableLiveData<Boolean>()

    init {
        val placeDao = GrabberApplication.placeDatabase.placeDao()
        placeRepository = PlaceRepository(placeDao)

        allLivePlaces = placeRepository.getAllLivePlaces()
        viewModelScope.launch {
            placeCount = placeRepository.getPlaceCount()
            Log.d("DBNUM", "${placeCount} db adat volt az adatbázisban.")
        }
    }

    fun updateCache(apiKey: String) {
//        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        val client : OkHttpClient = OkHttpClient.Builder().apply {
//            addInterceptor(interceptor)
//        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            //.addConverterFactory(GsonConverterFactory.create())
            //.client(client)
            .build()
        val nearbyAPI = retrofit.create(NearbySearchAPI::class.java)

        val lat = LocationService.lastLocation.latitude
        val lng = LocationService.lastLocation.longitude
        val formattedLocation: String = lat.toString() + ',' + lng.toString()

        val nearbyCall = nearbyAPI.getResults("store", formattedLocation,  "500", apiKey)
        nearbyCall.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                cacheUpdated.value = false
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val nearbySearchResult = Gson().fromJson(response.body().toString(), NearbySearchResult::class.java)

                for ((i, result) in nearbySearchResult.results.withIndex()) {
                    val temp = Place(
                        0,
                        nearbySearchResult.results[i].business_status,
                        LatLng(nearbySearchResult.results[i].geometry.location.lat, nearbySearchResult.results[i].geometry.location.lng),
                        nearbySearchResult.results[i].name,
                        nearbySearchResult.results[i].types,
                        nearbySearchResult.status
                    )
                    if (temp !in allCachedPlaces)
                        allCachedPlaces.add(temp)
                }
                cacheUpdated.value = true
            }
        })
    }

    fun debugAllCachedPlaces() {
        for ((i,place) in allCachedPlaces.withIndex()) {
            Log.d("${i}. HELY: ", place.name)
        }
    }




    fun getPlace(id: Int)
    {
        viewModelScope.launch {
            returnedPlace = placeRepository.getPlaceById(id)
        }
    }
    fun getAllLivePlaces() {
        viewModelScope.launch {
            allLivePlaces = placeRepository.getAllLivePlaces()
        }
    }

    fun getPlaceCount() {
        viewModelScope.launch {
            Log.d("ELEMEK_SZAMA: ",placeRepository.getPlaceCount().toString())
        }
    }

    fun insert(place: Place) = viewModelScope.launch {
        placeRepository.insertPlace(place)
        placeCount++
    }

    fun insertMore(places: List<Place>) = viewModelScope.launch {
        placeRepository.insertMorePlaces(places)
        placeCount += places.size
    }

    fun update(place: Place) = viewModelScope.launch {
        placeRepository.updatePlace(place)
    }

    fun save(places: List<Place>) = viewModelScope.launch {
        placeRepository.savePlaces(places)
        placeCount = places.size
    }

    fun delete(place: Place) = viewModelScope.launch {
        placeRepository.deletePlace(place)
        placeCount--
    }

    fun deleteAll() = viewModelScope.launch {
        placeRepository.deleteAllPlaces()
        placeCount = 0
    }
}