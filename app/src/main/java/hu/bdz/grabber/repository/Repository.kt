package hu.bdz.grabber.repository

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bdz.grabber.database.ItemDao
import hu.bdz.grabber.database.RoomItem
import hu.bdz.grabber.model.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val itemDao: ItemDao) {

    fun getAllItems(): LiveData<List<ListItem>> {
        return itemDao.getAllItems()
            .map {roomItems ->
                roomItems.map {roomItem ->
                    roomItem.toDomainModel() }
            }
    }

    suspend fun getItemCount() = withContext(Dispatchers.IO) {
        itemDao.getItemCount()
    }

    suspend fun getItem(id: Int) = withContext(Dispatchers.IO) {
        itemDao.getItemById(id)?.toDomainModel()
    }

    suspend fun insert(item: ListItem) = withContext(Dispatchers.IO) {
        itemDao.insertItem(item.toRoomModel())
    }

    suspend fun delete(item: ListItem) = withContext(Dispatchers.IO) {
        val roomItem = itemDao.getItemById(item.id) ?: return@withContext
        itemDao.deleteItem(roomItem)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        itemDao.deleteAllItems()
    }

    private fun RoomItem.toDomainModel(): ListItem {
        return ListItem(
            id = id,
            text = text
        )
    }

    private fun ListItem.toRoomModel(): RoomItem {
        return RoomItem(
            id = id,
            text = text
        )
    }
}
