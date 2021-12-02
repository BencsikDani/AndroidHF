package hu.bdz.grabber.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bdz.grabber.database.place.PlaceDao
import hu.bdz.grabber.database.place.RoomPlace
import hu.bdz.grabber.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository(private val placeDao: PlaceDao) {

    fun getAllLivePlaces(): LiveData<List<Place>> {
        return placeDao.getAllLivePlaces()
            .map {roomPlaces ->
                roomPlaces.map {roomPlace ->
                    roomPlace.toDomainModel() }
            }
    }

    suspend fun getAllPlaces() = withContext(Dispatchers.IO) {
        val allRoomPlaces = placeDao.getAllPlaces()
        var allDomainPlaces = mutableListOf<Place>()
        for (roomPlace in allRoomPlaces) {
            allDomainPlaces.add(roomPlace.toDomainModel())
        }
        allDomainPlaces
    }

    suspend fun getPlaceCount() = withContext(Dispatchers.IO) {
        placeDao.getPlaceCount()
    }

    suspend fun getPlaceById(id: Int) = withContext(Dispatchers.IO) {
        placeDao.getPlaceById(id)?.toDomainModel()
    }

    suspend fun insertPlace(place: Place) = withContext(Dispatchers.IO) {
        placeDao.insertPlace(place.toRoomModel())
    }

    suspend fun insertMorePlaces(places: List<Place>) = withContext(Dispatchers.IO) {
        for (place: Place in places) {
            placeDao.insertPlace(place.toRoomModel())
        }
    }

    suspend fun updatePlace(place: Place) = withContext(Dispatchers.IO) {
        placeDao.updatePlace(place.toRoomModel())
    }

    suspend fun deletePlace(place: Place) = withContext(Dispatchers.IO) {
        val roomPlace = placeDao.getPlaceById(place.placeId) ?: return@withContext
        placeDao.deletePlace(roomPlace)
    }

    suspend fun deleteAllPlaces() = withContext(Dispatchers.IO) {
        placeDao.deleteAllPlaces()
    }

    private fun RoomPlace.toDomainModel(): Place {
        return Place(
            placeId = placeId,
            businessStatus = businessStatus,
            location = location,
            name = name,
            types = types,
            status = status
        )
    }

    private fun Place.toRoomModel(): RoomPlace {
        return RoomPlace(
            placeId = placeId,
            businessStatus = businessStatus,
            location = location,
            name = name,
            types = types,
            status = status
        )
    }
}
