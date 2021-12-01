package hu.bdz.grabber.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bdz.grabber.R
import hu.bdz.grabber.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        val schonherz = LatLng(47.4729685, 19.0527766)
        googleMap.addMarker(MarkerOptions().position(schonherz).title("Marker for Schönherz"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(schonherz))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(schonherz, 16F))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}