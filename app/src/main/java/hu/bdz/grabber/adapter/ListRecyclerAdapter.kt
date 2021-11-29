package hu.bdz.grabber.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bdz.grabber.databinding.LayoutListItemBinding
import hu.bdz.grabber.model.ListItem
import hu.bdz.grabber.ui.list.ListViewModel

class ListRecyclerAdapter(val listViewModel: ListViewModel) : RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>()
{
    //private var itemList = emptyList<ListItem>()
    //private lateinit var itemList: MutableList<ListItem>

    var itemList = mutableListOf(
        ListItem(1, "Egy"),
        ListItem(2, "Kettő"),
        ListItem(3, "Három"),
        ListItem(4, "Négy"),
        ListItem(5, "Öt")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listViewModel.getItem(position)
        val currentItem = listViewModel.returnedItem.value
        holder.tvText.text = currentItem?.text
    }

    override fun getItemCount(): Int {
//        listViewModel.getAllItems()
//        return listViewModel.allItems.value?.size!!
        return 2
    }

    inner class ViewHolder(val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var tvText = binding.itemText
    }

    public fun addItem(item: ListItem)
    {
        itemList.add(item)
        notifyItemInserted(itemList.lastIndex)
    }

    private fun deleteItem(position: Int)
    {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}