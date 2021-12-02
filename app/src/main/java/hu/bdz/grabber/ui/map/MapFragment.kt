package hu.bdz.grabber.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import hu.bdz.grabber.service.LocationService

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        lateinit var mapFragment: SupportMapFragment
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
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
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
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
            googleMap.clear()

            val markerHU = googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Hello")
                    .snippet("Bello")
                    .flat(false))
            markerHU?.isDraggable = true

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }
    }
}