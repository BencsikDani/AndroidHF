package hu.bdz.grabber.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import hu.bdz.grabber.MainActivity
import hu.bdz.grabber.R
import hu.bdz.grabber.location.LocationHelper


class LocationService : Service() {

    companion object {
        const val BR_NEW_LOCATION = "BR_NEW_LOCATION"
        const val KEY_LOCATION = "KEY_LOCATION"
        const val NOTIFICATION1_ID = 22
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val NOTIFICATION2_ID = 23

        var lastLocation: Location? = null
            private set
    }

    private var locationHelper: LocationHelper? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION1_ID, createNotification("Helymeghatározás megkezdése..."))

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

        val contentIntent = PendingIntent.getActivity(this, NOTIFICATION1_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Grabber helymeghatározás")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_stat_icon)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(contentIntent)
            .setSilent(true)
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
        notificationManager.notify(NOTIFICATION1_ID, notification)
    }

    override fun onDestroy() {
        locationHelper?.stopLocationMonitoring()
        super.onDestroy()
    }


    inner class LocationServiceCalllback : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val location = result.lastLocation ?: return
            lastLocation = location

            updateNotification("É.sz.: ${location.latitude}  K.h.: ${location.longitude}")
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            if (p0.isLocationAvailable)
                updateNotification("Az Ön helyzete elérhető!")
            else
                updateNotification("Elvesztettük a helyzetét.")
        }
    }
}