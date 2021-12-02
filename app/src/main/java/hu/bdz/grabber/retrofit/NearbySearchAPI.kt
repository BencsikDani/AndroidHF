package hu.bdz.grabber.retrofit

import hu.bdz.grabber.model.nearbysearch.NearbySearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// base: https://maps.googleapis.com/

interface NearbySearchAPI {

    @GET("/maps/api/place/nearbysearch/json")
    fun getResults(
        @Query("type") type: String,
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("key") key: String
    ) : Call<NearbySearchResult>
}