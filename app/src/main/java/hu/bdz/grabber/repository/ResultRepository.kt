package hu.bdz.grabber.repository

import hu.bdz.grabber.database.place.results.ResultDao
import hu.bdz.grabber.database.place.results.RoomResult
import hu.bdz.grabber.model.nearbysearch.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ResultRepository(private val resultDao: ResultDao) {

    suspend fun insert(result: Result) = withContext(Dispatchers.IO) {
        resultDao.insertResult(result.toRoomModel())
    }

    suspend fun delete(result: Result) = withContext(Dispatchers.IO) {
        val roomResult = resultDao.getResultById(result.id) ?: return@withContext
        resultDao.deleteResult(roomResult)
    }

    private fun RoomResult.toDomainModel(): Result {
        return Result(
            id = id,
            business_status = business_status,
            geometry = geometry,
            icon = icon,
            icon_background_color = icon_background_color,
            icon_mask_base_uri = icon_mask_base_uri,
            name = name,
            opening_hours = opening_hours,
            permanently_closed = permanently_closed,
            photos = photos,
            place_id = place_id,
            plus_code = plus_code,
            price_level = price_level,
            rating = rating,
            reference = reference,
            scope = scope,
            types = types,
            user_ratings_total = user_ratings_total,
            vicinity = vicinity
        )
    }

    private fun Result.toRoomModel(): RoomResult {
        return RoomResult(
            id = id,
            business_status = business_status,
            geometry = geometry,
            icon = icon,
            icon_background_color = icon_background_color,
            icon_mask_base_uri = icon_mask_base_uri,
            name = name,
            opening_hours = opening_hours,
            permanently_closed = permanently_closed,
            photos = photos,
            place_id = place_id,
            plus_code = plus_code,
            price_level = price_level,
            rating = rating,
            reference = reference,
            scope = scope,
            types = types,
            user_ratings_total = user_ratings_total,
            vicinity = vicinity
        )
    }
}