package hu.bdz.grabber.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bdz.grabber.GrabberApplication
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import hu.bdz.grabber.repository.PlaceRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val placeRepository: PlaceRepository

    var allPlaces: LiveData<List<NearbySearchResult>>
    var placeCount: Int = 0

    var returnedPlace = MutableLiveData<NearbySearchResult>()

    init {
        val placeDao = GrabberApplication.placeDatabase.placeDao()
        placeRepository = PlaceRepository(placeDao)
        allPlaces = placeRepository.getAllPlaces()
        viewModelScope.launch {
            placeCount = placeRepository.getPlaceCount()
        }
    }

    fun getPlace(id: Int)
    {
        viewModelScope.launch {
            returnedPlace.value = placeRepository.getPlace(id)
        }
    }

    fun getAllPlaces()
    {
        viewModelScope.launch {
            allPlaces = placeRepository.getAllPlaces()
        }
    }

    fun insert(place: NearbySearchResult) = viewModelScope.launch {
        placeRepository.insert(place)
        placeCount++
    }

    fun insertMore(places: List<NearbySearchResult>) = viewModelScope.launch {
        placeRepository.insertMore(places)
        placeCount += places.size
    }

    fun update(place: NearbySearchResult) = viewModelScope.launch {
        placeRepository.update(place)
    }

    fun delete(place: NearbySearchResult) = viewModelScope.launch {
        placeRepository.delete(place)
        placeCount--
    }

    fun deleteAll() = viewModelScope.launch {
        placeRepository.deleteAll()
        placeCount = 0
    }
}