package hu.bdz.grabber.model

data class ListItem(
    var id: Int,
    var name: String,
    var brand: String,
    var quantity: String,
    var category: Category,
    var note: String,
    var bought: Boolean
) {
    enum class Category {
        GIFT, HEALTH, ELECTRONICS, FOOD, FILM_MUSIC, PET, HOBBY, STATIONERY, HOME_HOUSEHOLD, CLOTHING, SPORT, OTHER
    }

    constructor(
        name: String,
        brand: String,
        quantity: String,
        category: Category,
        note: String,
        bought: Boolean
    ) : this(0, name, brand, quantity, category, note, bought) { }
}