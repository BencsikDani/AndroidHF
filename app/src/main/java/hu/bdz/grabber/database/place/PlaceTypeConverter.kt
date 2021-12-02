package hu.bdz.grabber.database.place;

import android.location.Location
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

public class PlaceTypeConverter {
    @TypeConverter
    fun StringList_to_String(value: List<String>): String {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().toJson(value, type)
    }
    @TypeConverter
    fun String_to_StringList(value: String): List<String> {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun LatLng_to_String(value: LatLng): String {
        return value.latitude.toString() + ";" + value.longitude.toString()
    }
    @TypeConverter
    fun String_to_LatLng(value: String): LatLng {
        val strings: List<String> = value.split(";")
        return LatLng(strings[0].toDouble(), strings[1].toDouble())
    }
}
