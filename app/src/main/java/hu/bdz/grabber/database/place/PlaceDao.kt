package hu.bdz.grabber.database.place

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bdz.grabber.database.item.RoomItem

@Dao
interface PlaceDao {
    @Insert
    fun insertPlace(place: RoomPlace)

    @Query("SELECT * FROM place WHERE id == :id")
    fun getPlaceById(id: Int?): RoomPlace?

    @Query("SELECT * FROM place")
    fun getAllPlaces(): LiveData<List<RoomPlace>>

    @Update
    fun updatePlace(place: RoomPlace): Int

    @Delete
    fun deletePlace(place: RoomPlace)

    @Query("DELETE FROM place")
    fun deleteAllPlaces()

    @Query("SELECT COUNT(*) FROM place")
    fun getPlaceCount(): Int
}