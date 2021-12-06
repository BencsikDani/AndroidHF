package hu.bdz.grabber.service

import hu.bdz.grabber.model.ListItem

class TypeCollector {
    companion object {
        fun getMergedPlaceTypesFromListItems(items: List<ListItem>?): List<String> {
            if (items != null)
            {
                var mergedPlaceTypes = mutableListOf<String>()
                for (category in getCategoriesFromListItems(items)) {
                    for (type in getPlaceTypesFromCategory(category)) {
                        if (type !in mergedPlaceTypes)
                            mergedPlaceTypes.add(type)
                    }
                }
                return mergedPlaceTypes
            }
            return emptyList()
        }

        // Milyen kategóriákat tartalmaz a felhasználó listája?
        fun getCategoriesFromListItems(items: List<ListItem>): List<ListItem.Category> {
            var categories = mutableListOf<ListItem.Category>()
            for (item: ListItem in items) {
                if (item.category !in categories)
                    categories.add(item.category)
            }
            return categories
        }

        // Egy kategóriához milyen típusú boltok tartozhatnak?
        fun getPlaceTypesFromCategory(category: ListItem.Category): List<String> {
            if (category == ListItem.Category.GIFT)
                return listOf("book_store",
                    "clothing_store",
                    "convenience_store",
                    "department_store",
                    "electronics_store",
                    "home_goods_store",
                    "jewelry_store",
                    "liquor_store",
                    "shopping_mall",
                    "supermarket")
            else if (category == ListItem.Category.HEALTH)
                return listOf("department_store",
                    "drugstore",
                    "pharmacy",
                    "shopping_mall",
                    "supermarket")
            else if (category == ListItem.Category.ELECTRONICS)
                return listOf("electronics_store",
                    "hardware_store",
                    "shopping_mall")
            else if (category == ListItem.Category.FOOD)
                return listOf("bakery",
                    "convenience_store",
                    "department_store",
                    "liquor_store",
                    "meal_takeaway",
                    "shopping_mall",
                    "supermarket")
            else if (category == ListItem.Category.FILM_MUSIC)
                return listOf("electronics_store",
                    "movie_rental",
                    "shopping_mall")
            else if (category == ListItem.Category.PET)
                return listOf("convenience_store",
                    "pet_store",
                    "shopping_mall")
            else if (category == ListItem.Category.HOBBY)
                return listOf("bicycle_store",
                    "book_store",
                    "department_store",
                    "electronics_store",
                    "hardware_store",
                    "home_goods_store",
                    "movie_rental",
                    "shopping_mall")
            else if (category == ListItem.Category.STATIONERY)
                return listOf("department_store",
                    "shopping_mall")
            else if (category == ListItem.Category.HOME_HOUSEHOLD)
                return listOf("electronics_store",
                    "furniture_store",
                    "hardware_store",
                    "shopping_mall",)
            else if (category == ListItem.Category.CLOTHING)
                return listOf("clothing_store",
                    "jewelry_store",
                    "shoe_store",
                    "shopping_mall")
            else if (category == ListItem.Category.SPORT)
                return listOf("bicycle_store",
                    "shopping_mall")
            else
                return emptyList<String>()
        }
    }
}