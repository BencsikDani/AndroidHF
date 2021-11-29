package hu.bdz.grabber.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class RoomItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)