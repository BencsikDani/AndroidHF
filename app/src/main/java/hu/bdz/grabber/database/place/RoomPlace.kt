package hu.bdz.grabber.database.place

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bdz.grabber.model.nearbysearch.Result

@Entity(tableName = "place")
data class RoomPlace(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)