package hu.bdz.grabber

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bdz.grabber.databinding.ActivityMainBinding
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.service.LocationService
import hu.bdz.grabber.service.TypeCollector
import hu.bdz.grabber.ui.map.MapFragment
import hu.bdz.grabber.ui.list.ListFragment

class MainActivity : AppCompatActivity() {
    val apiKey = "AIzaSyA64m1S-Q9KThDLAJqMVWrPC7pR4-Gmw-A"

    companion object {
        const val KEY_IS_LOCATION_SERVICE_RUNNING = "is_location_service_running"
    }

    private lateinit var binding: ActivityMainBinding

    lateinit var locationService: LocationService
        private set

    lateinit var placesClient: PlacesClient
        private set

    private var allItems: List<ListItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.setIcon(R.drawable.ic_action_icon)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_list, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        locationService = LocationService()
        resetLocationService()


        Places.initialize(applicationContext, apiKey)
        placesClient = Places.createClient(this)

        ListFragment.allLiveItems.observe(this, { items ->
            allItems = items
        })
    }

    fun resetLocationService() {
        val sp = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val isLocationServiceRunning = sp.getBoolean(KEY_IS_LOCATION_SERVICE_RUNNING, false)
        val intent = Intent(applicationContext, LocationService::class.java)

        stopService(intent)

        if (isLocationServiceRunning) {
            startService(intent)
        }
    }

    fun beginPlaceMagic() {
        val placeTypes = TypeCollector.getMergedPlaceTypesFromListItems(allItems)
        MapFragment.beginUpdatingFullCache(placeTypes, apiKey)
    }
}