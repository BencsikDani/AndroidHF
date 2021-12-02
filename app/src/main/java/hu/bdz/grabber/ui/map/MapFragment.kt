package hu.bdz.grabber.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bdz.grabber.MainActivity
import hu.bdz.grabber.R
import hu.bdz.grabber.databinding.FragmentMapBinding
import hu.bdz.grabber.model.Place
import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import hu.bdz.grabber.retrofit.NearbySearchAPI
import hu.bdz.grabber.service.LocationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        lateinit var mapFragment: SupportMapFragment
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapViewModel: MapViewModel

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        mapViewModel.allLivePlaces.observe(viewLifecycleOwner, { places ->
            // TODO itt frissíteni kéne tán a térképet??
        })

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.mvGoogleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        updatePlaceDatabase()
    }

    fun updatePlaceDatabase() {
//        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        val client : OkHttpClient = OkHttpClient.Builder().apply {
//            addInterceptor(interceptor)
//        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            //.addConverterFactory(GsonConverterFactory.create())
            //.client(client)
            .build()
        val nearbyAPI = retrofit.create(NearbySearchAPI::class.java)

        val lat = LocationService.lastLocation.latitude
        val lng = LocationService.lastLocation.longitude
        val formattedLocation: String = lat.toString() + ',' + lng.toString()

        val nearbyCall = nearbyAPI.getResults("bar", formattedLocation,  "1500", (activity as MainActivity).apiKey)
        nearbyCall.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "A csatlakozás sikertelen: " + t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val nearbySearchResult = Gson().fromJson(response.body().toString(), NearbySearchResult::class.java)
                var places: MutableList<Place> = mutableListOf<Place>()

                for ((i, result) in nearbySearchResult.results.withIndex()) {
                    places.add(Place(
                        0,
                        nearbySearchResult.results[i].business_status,
                        LatLng(nearbySearchResult.results[i].geometry.location.lat, nearbySearchResult.results[i].geometry.location.lng),
                        nearbySearchResult.results[i].name,
                        nearbySearchResult.results[i].types,
                        nearbySearchResult.status
                    ))
                }
                mapViewModel.insertMore(places)

                mapViewModel.getPlaceCount()
                mapViewModel.getAllPlaces()
            }
        })
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

    fun drawMarker(googleMap: GoogleMap, place: NearbySearchResult) {
//        googleMap.addMarker(MarkerOptions()
//            .position(LatLng(place.results.geo)
//            .title()
//            .flat(false)
//            .draggable(false)
//        )
    }
}