package hu.bdz.grabber.database.place.results

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bdz.grabber.model.nearbysearch.Result

class ResultTypeConverter {
    @TypeConverter
    fun Result_to_String(value: Result): String {
        val type = object: TypeToken<Result>() {}.type
        return Gson().toJson(value, type)
    }
    @TypeConverter
    fun String_to_Result(value: String): Result {
        val type = object: TypeToken<Result>() {}.type
        return Gson().fromJson(value, type)
    }
}