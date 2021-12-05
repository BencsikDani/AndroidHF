package hu.bdz.grabber.ui.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
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
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.model.Place
import hu.bdz.grabber.service.LocationService

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapFragment: SupportMapFragment

    private var googleMap: GoogleMap? = null

    companion object {
        private var isMapReady = false
        private lateinit var mapViewModel: MapViewModel

        fun beginUpdatingFullCache(placeTypes: List<String>, apiKey: String) {
            mapViewModel.updateFullCache(placeTypes, apiKey)
//            val tmp = listOf<String>("store")
//            mapViewModel.updateFullCache(tmp, apiKey)
        }

        fun redrawMarkers(map: GoogleMap, places: MutableList<Place>) : Int {
            var done = 0
            if (isMapReady) {
                map.clear()
                for (place in places) {
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(place.location.latitude, place.location.longitude))
                            .title(place.name)
                            .snippet(place.types.toString())
                            .flat(false)
                            .draggable(false)
                    )
                    done++
                }
            }
            return done
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mapViewModel = ViewModelProvider(activity as MainActivity).get(MapViewModel::class.java)
        mapViewModel.allLivePlaces.observe(viewLifecycleOwner, {
            // TODO dunno
        })
        mapViewModel.allPlaceAreCached.observe(viewLifecycleOwner, { state ->
            if (googleMap != null)
            {
                if (state == 0) {}
                else if (state == 1) {
                    redrawMarkers(googleMap!!, mapViewModel.allCachedPlaces)
                    Toast.makeText(context, "${mapViewModel.placeCached} db találat!", Toast.LENGTH_SHORT).show()
                }
                else if (state == -1)
                    Toast.makeText(context, "A csatlakozás sikertelen!", Toast.LENGTH_LONG).show()
            }
        })

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.frMap) as SupportMapFragment
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

        if (LocationService.lastLocation != null)
        {
            (activity as MainActivity).beginPlaceMagic()
        }

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context?.applicationContext!!, R.raw.map_style_json))
        googleMap.setPadding(20, 20, 20, 20)
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.isMyLocationEnabled = true
        googleMap.isBuildingsEnabled = true

        if (LocationService.lastLocation != null)
        {
            val lat = LocationService.lastLocation!!.latitude
            val lng = LocationService.lastLocation!!.longitude
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 16F))
        }

        googleMap.setOnMapLongClickListener {

            val markerHU = googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Ide")
                    .snippet("böktél")
                    .flat(false))
            markerHU?.isDraggable = true

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))

            (activity as MainActivity).beginPlaceMagic()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshMap -> {
                //mapViewModel.updateCache("store", (activity as MainActivity).apiKey)
            }
        }
        return true
    }
}