package hu.bdz.grabber.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bdz.grabber.GrabberApplication
import hu.bdz.grabber.model.Place
import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import hu.bdz.grabber.repository.PlaceRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val placeRepository: PlaceRepository

    var allLivePlaces: LiveData<List<Place>>
    var allPlaces = mutableListOf<Place>()

    var placeCount: Int = 0

    var returnedPlace: Place? = null

    init {
        val placeDao = GrabberApplication.placeDatabase.placeDao()
        placeRepository = PlaceRepository(placeDao)
        allLivePlaces = placeRepository.getAllLivePlaces()
        viewModelScope.launch {
            placeCount = placeRepository.getPlaceCount()
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

    fun getAllPlaces()
    {
        viewModelScope.launch {
            allPlaces = placeRepository.getAllPlaces()
            for ((i, place) in allPlaces.withIndex()) {
                Log.d("${i}.ELEM: ", place.name)
            }
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

    fun delete(place: Place) = viewModelScope.launch {
        placeRepository.deletePlace(place)
        placeCount--
    }

    fun deleteAll() = viewModelScope.launch {
        placeRepository.deleteAllPlaces()
        placeCount = 0
    }
}