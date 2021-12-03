package hu.bdz.grabber.ui.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import hu.bdz.grabber.MainActivity
import hu.bdz.grabber.R
import hu.bdz.grabber.databinding.FragmentMapBinding
import hu.bdz.grabber.model.Place
import hu.bdz.grabber.service.LocationService

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mapViewModel: MapViewModel

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mapViewModel.allLivePlaces.observe(viewLifecycleOwner, {
            // TODO dunno
        })
        mapViewModel.cacheUpdated.observe(viewLifecycleOwner, { apiResponse ->
            apiResponse?.let {
                val result = mapViewModel.cacheUpdated.value
                mapViewModel.cacheUpdated.value = null
                if (result == true) {
                    redrawMarkers(googleMap, mapViewModel.allCachedPlaces)
                    Toast.makeText(context, "${mapViewModel.allCachedPlaces.size} db találat!", Toast.LENGTH_LONG).show()
                }
                else if (result == false)
                    Toast.makeText(context, "A csatlakozás sikertelen!", Toast.LENGTH_LONG).show()
            }
        })

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.mvGoogleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        mapViewModel.save(mapViewModel.allCachedPlaces)
        isMapReady = false
        _binding = null
        super.onDestroyView()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        isMapReady = true
        this.googleMap = googleMap

        mapViewModel.updateCache((activity as MainActivity).apiKey)

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context?.applicationContext!!, R.raw.map_style_json))
        googleMap.setPadding(20, 20, 20, 20)
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.isMyLocationEnabled = true
        googleMap.isBuildingsEnabled = true

        val lat = LocationService.lastLocation.latitude
        val lng = LocationService.lastLocation.longitude
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 16F))

        googleMap.setOnMapLongClickListener {

            val markerHU = googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Ide")
                    .snippet("böktél")
                    .flat(false))
            markerHU?.isDraggable = true

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }
    }

    companion object {
        private var isMapReady = false

        fun redrawMarkers(map: GoogleMap, places: MutableList<Place>) : Int {
            var done = 0
            if (isMapReady) {
                map.clear()
                for (place in places) {
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(place.location.latitude, place.location.longitude))
                            .title(place.name)
                            .flat(false)
                            .draggable(false)
                    )
                    done++
                }
            }
            return done
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshMap -> {
                mapViewModel.updateCache((activity as MainActivity).apiKey)
            }
        }
        return true
    }
}