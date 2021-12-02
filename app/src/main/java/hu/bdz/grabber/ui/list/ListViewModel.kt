package hu.bdz.grabber.ui.list

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

    var allItems: LiveData<List<ListItem>>
    var itemCount: Int = 0

    var returnedItem = MutableLiveData<ListItem>()

    init {
        val itemDao = GrabberApplication.itemDatabase.itemDao()
        listRepository = ListRepository(itemDao)
        allItems = listRepository.getAllItems()
        viewModelScope.launch {
            itemCount = listRepository.getItemCount()
        }
    }

    fun getItem(id: Int)
    {
        viewModelScope.launch {
            returnedItem.value = listRepository.getItem(id)
        }
    }

    fun getAllItems()
    {
        viewModelScope.launch {
            allItems = listRepository.getAllItems()
        }
    }

    fun insert(item: ListItem) = viewModelScope.launch {
        listRepository.insert(item)
        itemCount++
    }

    fun update(item: ListItem) = viewModelScope.launch {
        listRepository.update(item)
    }

    fun delete(item: ListItem) = viewModelScope.launch {
        listRepository.delete(item)
        itemCount--
    }

    fun deleteBought() = viewModelScope.launch {
        listRepository.deleteBought()
        itemCount = listRepository.getItemCount()
    }

    fun deleteAll() = viewModelScope.launch {
        listRepository.deleteAll()
        itemCount = 0
    }
}