package hu.bdz.grabber

import android.app.Application
import androidx.room.Room
import hu.bdz.grabber.database.ItemDatabase

class GrabberApplication : Application() {

    companion object {
        lateinit var itemDatabase: ItemDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        itemDatabase = Room.databaseBuilder(
            applicationContext,
            ItemDatabase::class.java,
            "item_database"
        ).fallbackToDestructiveMigration().build()
    }
}