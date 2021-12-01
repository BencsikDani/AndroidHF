package hu.bdz.grabber.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bdz.grabber.databinding.LayoutListItemBinding
import hu.bdz.grabber.model.ListItem

class ListRecyclerAdapter : ListAdapter<ListItem, ListRecyclerAdapter.ViewHolder>(itemCallback)
{
    var itemClickListener: ItemClickListener? = null

    companion object{
        object itemCallback : DiffUtil.ItemCallback<ListItem>(){
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = this.getItem(position)

        holder.item = currentItem
        holder.binding.itemText.text = currentItem.text
    }

    inner class ViewHolder(val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var item: ListItem? = null

        init {
            itemView.setOnLongClickListener { view ->
                item?.let { item ->
                    itemClickListener?.onItemLongClick(bindingAdapterPosition, view, item)
                }
                true
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(item: ListItem)
        fun onItemLongClick(position: Int, view: View, item: ListItem): Boolean
    }
}