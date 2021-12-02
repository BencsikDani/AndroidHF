package hu.bdz.grabber.database.place

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

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
