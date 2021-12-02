package hu.bdz.grabber.database.item

import androidx.room.TypeConverter
import hu.bdz.grabber.model.ListItem

class ItemTypeConverter {
    companion object {
        const val GIFT = "GIFT"
        const val HEALTH = "HEALTH"
        const val ELECTRONICS = "ELECTRONICS"
        const val FOOD = "FOOD"
        const val FILM_MUSIC = "FILM_MUSIC"
        const val PET = "PET"
        const val HOBBY = "HOBBY"
        const val STATIONERY = "STATIONERY"
        const val HOME_HOUSEHOLD = "HOME_HOUSEHOLD"
        const val CLOTHING = "CLOTHING"
        const val SPORT = "SPORT"
        const val OTHER = "OTHER"
    }

    @TypeConverter
    fun toCategory(value: String?): ListItem.Category {
        return when (value) {
            GIFT -> ListItem.Category.GIFT
            HEALTH -> ListItem.Category.HEALTH
            ELECTRONICS -> ListItem.Category.ELECTRONICS
            FOOD -> ListItem.Category.FOOD
            FILM_MUSIC -> ListItem.Category.FILM_MUSIC
            PET -> ListItem.Category.PET
            HOBBY -> ListItem.Category.HOBBY
            STATIONERY -> ListItem.Category.STATIONERY
            HOME_HOUSEHOLD -> ListItem.Category.HOME_HOUSEHOLD
            CLOTHING -> ListItem.Category.CLOTHING
            SPORT -> ListItem.Category.SPORT
            OTHER -> ListItem.Category.OTHER

            else -> ListItem.Category.OTHER
        }
    }

    @TypeConverter
    fun toString(enumValue: ListItem.Category): String? {
        return enumValue.name
    }
}