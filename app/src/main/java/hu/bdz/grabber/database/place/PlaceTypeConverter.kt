package hu.bdz.grabber.database.place;

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bdz.grabber.model.nearbysearch.Result
import java.lang.reflect.Type

@ProvidedTypeConverter
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
}
