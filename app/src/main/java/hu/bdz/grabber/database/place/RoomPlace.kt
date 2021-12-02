package hu.bdz.grabber.database.place

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import hu.bdz.grabber.model.nearbysearch.Location

@Entity(tableName = "place")
data class RoomPlace(
    @PrimaryKey(autoGenerate = true)
    val placeId: Int = 0,
    val businessStatus: String,
    val location: LatLng,
    val name: String,
    val types: List<String>,
    val status: String
)