package hu.bdz.grabber.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.preference.PreferenceManager
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.SettingsApi
import hu.bdz.grabber.MainActivity
import hu.bdz.grabber.R
import hu.bdz.grabber.location.LocationHelper
import android.app.ActivityManager




class LocationService : Service() {

    companion object {
        const val BR_NEW_LOCATION = "BR_NEW_LOCATION"
        const val KEY_LOCATION = "KEY_LOCATION"
        const val NOTIFICATION_ID = 22
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    private var locationHelper: LocationHelper? = null

    var lastLocation: Location? = null
        private set

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification("Helymeghatározás kezdése..."))

        if (locationHelper == null) {
            val helper = LocationHelper(applicationContext, LocationServiceCalllback())
            helper.startLocationMonitoring()
            locationHelper = helper
        }
        return Service.START_STICKY
    }

    private fun createNotification(text: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        createNotificationChannel()

        val contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            //.setContentTitle("Grabber")
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(contentIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        locationHelper?.stopLocationMonitoring()
        super.onDestroy()
    }


    inner class LocationServiceCalllback : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return
            updateNotification("Lat: ${location.latitude} Lng: ${location.longitude}")

            lastLocation = location

//            val intent = Intent()
//            intent.action = BR_NEW_LOCATION
//            intent.putExtra(KEY_LOCATION, location)
//            LocalBroadcastManager.getInstance(this@LocationService).sendBroadcast(intent)
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            updateNotification("Elérhető helyzet: ${p0.isLocationAvailable}")
        }
    }
}