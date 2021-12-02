package hu.bdz.grabber.database.item

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 2,
    exportSchema = false,
    entities = [RoomItem::class]
)
@TypeConverters(
    ItemTypeConverter::class
)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

}
