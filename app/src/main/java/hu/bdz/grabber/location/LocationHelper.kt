package hu.bdz.grabber.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationHelper(private val context: Context, private val callback: LocationCallback) {

    @SuppressLint("MissingPermission")
    fun startLocationMonitoring() {
        val request = LocationRequest.create().apply {
            interval = 5000L
            fastestInterval = 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    fun stopLocationMonitoring() {
        LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(callback)
    }
}