package hu.bdz.grabber.database.place.results

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResultDao {
    @Insert
    fun insertResult(result: RoomResult)

    @Query("SELECT * FROM result WHERE id == :id")
    fun getResultById(id: Int?): RoomResult?

    @Delete
    fun deleteResult(result: RoomResult)
}