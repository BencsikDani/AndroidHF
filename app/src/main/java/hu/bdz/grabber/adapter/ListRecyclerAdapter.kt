package hu.bdz.grabber.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bdz.grabber.databinding.LayoutListItemBinding
import hu.bdz.grabber.model.ListItem

class ListRecyclerAdapter : ListAdapter<ListItem, ListRecyclerAdapter.ViewHolder>(itemCallback)
{
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
        holder.tvText.text = currentItem.id.toString() + ". - " + currentItem.text
    }

    inner class ViewHolder(val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var tvText = binding.itemText
    }
}