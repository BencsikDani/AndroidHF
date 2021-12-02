package hu.bdz.grabber.model

import com.google.android.gms.maps.model.LatLng

data class Place(
    var placeId: Int,
    var businessStatus: String,
    var location: LatLng,
    var name: String,
    var types: List<String>,
    var status: String
)