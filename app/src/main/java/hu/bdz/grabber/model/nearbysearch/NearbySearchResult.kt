package hu.bdz.grabber.model.nearbysearch

data class NearbySearchResult(
    var id: Int,
    var html_attributions: List<Any>,
    var next_page_token: String,
    var results: List<Result>,
    var status: String
)