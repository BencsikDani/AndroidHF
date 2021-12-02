package hu.bdz.grabber.database.place

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import hu.bdz.grabber.model.nearbysearch.Result

@Entity(tableName = "place")
data class RoomPlace(
    @PrimaryKey(autoGenerate = true) val placeId: Int = 0,
    val html_attributions: List<String>,
    val next_page_token: String,
    @Embedded val results: List<Result>,
    @Relation( parentColumn = "placeId", entityColumn = "resultId")
    val status: String
)