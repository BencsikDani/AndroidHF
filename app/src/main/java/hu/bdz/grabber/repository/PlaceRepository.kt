package hu.bdz.grabber.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bdz.grabber.database.place.PlaceDao
import hu.bdz.grabber.database.place.RoomPlace
import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaceRepository(private val placeDao: PlaceDao) {

    fun getAllPlaces(): LiveData<List<NearbySearchResult>> {
        return placeDao.getAllPlaces()
            .map {roomPlaces ->
                roomPlaces.map {roomPlace ->
                    roomPlace.toDomainModel() }
            }
    }

    suspend fun getPlaceCount() = withContext(Dispatchers.IO) {
        placeDao.getPlaceCount()
    }

    suspend fun getPlace(id: Int) = withContext(Dispatchers.IO) {
        placeDao.getPlaceById(id)?.toDomainModel()
    }

    suspend fun insert(place: NearbySearchResult) = withContext(Dispatchers.IO) {
        placeDao.insertPlace(place.toRoomModel())
    }

    suspend fun insertMore(places: List<NearbySearchResult>) = withContext(Dispatchers.IO) {
        for (place: NearbySearchResult in places) {
            placeDao.insertPlace(place.toRoomModel())
        }
    }

    suspend fun update(place: NearbySearchResult) = withContext(Dispatchers.IO) {
        placeDao.updatePlace(place.toRoomModel())
    }

    suspend fun delete(place: NearbySearchResult) = withContext(Dispatchers.IO) {
        val roomPlace = placeDao.getPlaceById(place.id) ?: return@withContext
        placeDao.deletePlace(roomPlace)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        placeDao.deleteAllPlaces()
    }

    private fun RoomPlace.toDomainModel(): NearbySearchResult {
        return NearbySearchResult(
            id = id,
            html_attributions = html_attributions,
            next_page_token = next_page_token,
            results = results,
            status = status
        )
    }

    private fun NearbySearchResult.toRoomModel(): RoomPlace {
        return RoomPlace(
            id = id,
            html_attributions = html_attributions,
            next_page_token = next_page_token,
            results = results,
            status = status
        )
    }
}
