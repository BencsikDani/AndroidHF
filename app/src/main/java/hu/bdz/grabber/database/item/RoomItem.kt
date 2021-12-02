package hu.bdz.grabber.database.item

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bdz.grabber.model.ListItem

@Entity(tableName = "item")
data class RoomItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val brand: String,
    val quantity: String,
    val category: ListItem.Category,
    val note: String,
    val bought: Boolean
)