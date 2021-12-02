package hu.bdz.grabber.database.place

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bdz.grabber.database.item.ItemDao
import hu.bdz.grabber.database.item.ItemTypeConverter
import hu.bdz.grabber.database.item.RoomItem

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomPlace::class]
)
@TypeConverters(
    PlaceTypeConverter::class
)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao
}
