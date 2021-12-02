package hu.bdz.grabber

import android.app.Application
import androidx.room.Room
import hu.bdz.grabber.database.item.ItemDatabase
import hu.bdz.grabber.database.place.PlaceDatabase

class GrabberApplication : Application() {

    companion object {
        lateinit var itemDatabase: ItemDatabase
            private set
        lateinit var placeDatabase: PlaceDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        itemDatabase = Room.databaseBuilder(
            applicationContext,
            ItemDatabase::class.java,
            "item_database"
        ).fallbackToDestructiveMigration().build()

        placeDatabase = Room.databaseBuilder(
            applicationContext,
            PlaceDatabase::class.java,
            "place_database"
        ).fallbackToDestructiveMigration().build()
    }
}