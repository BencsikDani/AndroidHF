package hu.bdz.grabber.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Insert
    fun insertItem(item: RoomItem)

    @Query("SELECT * FROM item WHERE id == :id")
    fun getItemById(id: Int?): RoomItem?

    @Query("SELECT * FROM item")
    fun getAllItems(): LiveData<List<RoomItem>>

    @Update
    fun updateItem(item: RoomItem): Int

    @Delete
    fun deleteItem(item: RoomItem)

    @Query("DELETE FROM item WHERE bought == 1")
    fun deleteBoughtItems()

    @Query("DELETE FROM item")
    fun deleteAllItems()

    @Query("SELECT COUNT(*) FROM item")
    fun getItemCount(): Int
}