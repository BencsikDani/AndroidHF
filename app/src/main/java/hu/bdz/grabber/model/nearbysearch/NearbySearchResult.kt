package hu.bdz.grabber.model.nearbysearch

data class NearbySearchResult(
    var html_attributions: List<String>,
    var next_page_token: String,
    var results: List<Result>,
    var status: String
)