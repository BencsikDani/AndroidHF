package hu.bdz.grabber.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bdz.grabber.GrabberApplication
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.repository.ListRepository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val listRepository: ListRepository

    var allLiveItems: LiveData<List<ListItem>>
    //var liveItemUpdate = MutableLiveData<List<ListItem>>()

    var allItems = mutableListOf<ListItem>()

    var itemCount: Int = 0

    var returnedItem: ListItem? = null

    init {
        val itemDao = GrabberApplication.itemDatabase.itemDao()
        listRepository = ListRepository(itemDao)
        allLiveItems = listRepository.getAllLiveItems()
        viewModelScope.launch {
            itemCount = listRepository.getItemCount()
        }
    }

    fun getItem(id: Int) {
        viewModelScope.launch {
            returnedItem = listRepository.getItemById(id)
        }
    }

    fun getAllLiveItems() {
        viewModelScope.launch {
            allLiveItems = listRepository.getAllLiveItems()
        }
    }

    fun getAllItems() {
        viewModelScope.launch {
            allItems = listRepository.getAllItems()
        }
    }

    fun getItemCount() {
        viewModelScope.launch {
            Log.d("ELEMEK_SZAMA:",listRepository.getItemCount().toString())
        }
    }

    fun insert(item: ListItem) = viewModelScope.launch {
        listRepository.insertItem(item)
        itemCount++
    }

    fun update(item: ListItem) = viewModelScope.launch {
        listRepository.updateItem(item)
    }

    fun delete(item: ListItem) = viewModelScope.launch {
        listRepository.deleteItem(item)
        itemCount--
    }

    fun deleteBought() = viewModelScope.launch {
        listRepository.deleteBoughtItems()
        itemCount = listRepository.getItemCount()
    }

    fun deleteAll() = viewModelScope.launch {
        listRepository.deleteAllItems()
        itemCount = 0
    }
}