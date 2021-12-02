package hu.bdz.grabber.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bdz.grabber.database.item.ItemDao
import hu.bdz.grabber.database.item.RoomItem
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepository(private val itemDao: ItemDao) {

    fun getAllLiveItems(): LiveData<List<ListItem>> {
        return itemDao.getAllLiveItems()
            .map {roomItems ->
                roomItems.map {roomItem ->
                    roomItem.toDomainModel() }
            }
    }

    suspend fun getAllItems() = withContext(Dispatchers.IO) {
        val allRoomItems = itemDao.getAllItems()
        var allDomainItems = mutableListOf<ListItem>()
        for (roomItem in allRoomItems) {
            allDomainItems.add(roomItem.toDomainModel())
        }
        allDomainItems
    }

    suspend fun getItemCount() = withContext(Dispatchers.IO) {
        itemDao.getItemCount()
    }

    suspend fun getItemById(id: Int) = withContext(Dispatchers.IO) {
        itemDao.getItemById(id)?.toDomainModel()
    }

    suspend fun insertItem(item: ListItem) = withContext(Dispatchers.IO) {
        itemDao.insertItem(item.toRoomModel())
    }

    suspend fun updateItem(item: ListItem) = withContext(Dispatchers.IO) {
        itemDao.updateItem(item.toRoomModel())
    }

    suspend fun deleteItem(item: ListItem) = withContext(Dispatchers.IO) {
        val roomItem = itemDao.getItemById(item.id) ?: return@withContext
        itemDao.deleteItem(roomItem)
    }

    suspend fun deleteBoughtItems() = withContext(Dispatchers.IO) {
        itemDao.deleteBoughtItems()
    }

    suspend fun deleteAllItems() = withContext(Dispatchers.IO) {
        itemDao.deleteAllItems()
    }

    private fun RoomItem.toDomainModel(): ListItem {
        return ListItem(
            id = id,
            name = name,
            brand = brand,
            quantity = quantity,
            category = category,
            note = note,
            bought = bought
        )
    }

    private fun ListItem.toRoomModel(): RoomItem {
        return RoomItem(
            id = id,
            name = name,
            brand = brand,
            quantity = quantity,
            category = category,
            note = note,
            bought = bought
        )
    }
}
