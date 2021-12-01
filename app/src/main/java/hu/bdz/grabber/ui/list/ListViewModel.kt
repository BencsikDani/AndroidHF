package hu.bdz.grabber.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bdz.grabber.GrabberApplication
import hu.bdz.grabber.database.RoomItem
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.repository.Repository
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val repository: Repository

    var allItems: LiveData<List<ListItem>>
    var itemCount: Int = 0

    var returnedItem = MutableLiveData<ListItem>()

    init {
        val itemDao = GrabberApplication.itemDatabase.itemDao()
        repository = Repository(itemDao)
        allItems = repository.getAllItems()
        viewModelScope.launch {
            itemCount = repository.getItemCount()
        }
    }

    fun getItem(id: Int)
    {
        viewModelScope.launch {
            returnedItem.value = repository.getItem(id)
        }
    }

    fun getAllItems()
    {
        viewModelScope.launch {
            allItems = repository.getAllItems()
        }
    }

    fun insert(item: ListItem) = viewModelScope.launch {
        repository.insert(item)
        itemCount++
    }

    fun update(item: ListItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: ListItem) = viewModelScope.launch {
        repository.delete(item)
        itemCount--
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
        itemCount = 0
    }
}