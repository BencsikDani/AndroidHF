package hu.bdz.grabber.database.place

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceDao {
    @Insert
    fun insertPlace(place: RoomPlace)

    @Query("SELECT * FROM place WHERE placeId == :id")
    fun getPlaceById(id: Int?): RoomPlace?

    @Query("SELECT * FROM place")
    fun getAllLivePlaces(): LiveData<List<RoomPlace>>

    @Query("SELECT * FROM place")
    fun getAllPlaces(): List<RoomPlace>

    @Update
    fun updatePlace(place: RoomPlace): Int

    @Delete
    fun deletePlace(place: RoomPlace)

    @Query("DELETE FROM place")
    fun deleteAllPlaces()

    @Query("SELECT COUNT(*) FROM place")
    fun getPlaceCount(): Int
}