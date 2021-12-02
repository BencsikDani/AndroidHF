package hu.bdz.grabber.database.place.results

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 2,
    exportSchema = false,
    entities = [RoomResult::class]
)
@TypeConverters(
    ResultTypeConverter::class
)
abstract class ResultDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao
}