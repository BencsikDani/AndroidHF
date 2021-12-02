package hu.bdz.grabber.database.place.results

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bdz.grabber.model.nearbysearch.Geometry
import hu.bdz.grabber.model.nearbysearch.OpeningHours
import hu.bdz.grabber.model.nearbysearch.Photo
import hu.bdz.grabber.model.nearbysearch.PlusCode

@Entity(tableName = "result")
data class RoomResult(
    @PrimaryKey(autoGenerate = true)
    val resultId: Int,
    val business_status: String,
    val geometry: Geometry,
    val icon: String,
    val icon_background_color: String,
    val icon_mask_base_uri: String,
    val name: String,
    val opening_hours: OpeningHours,
    val permanently_closed: Boolean,
    val photos: List<Photo>,
    val place_id: String,
    val plus_code: PlusCode,
    val price_level: Int,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val user_ratings_total: Int,
    val vicinity: String
)