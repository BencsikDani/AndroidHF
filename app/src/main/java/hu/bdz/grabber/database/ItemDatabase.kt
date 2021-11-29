package hu.bdz.grabber.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomItem::class]
)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

}
