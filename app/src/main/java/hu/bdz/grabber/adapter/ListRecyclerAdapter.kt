package hu.bdz.grabber.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bdz.grabber.R
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

        holder.binding.checkBox.isChecked = currentItem.bought
        val image = when (currentItem.category) {
            ListItem.Category.GIFT -> R.drawable.ic_gift
            ListItem.Category.HEALTH -> R.drawable.ic_health
            ListItem.Category.ELECTRONICS -> R.drawable.ic_electronics
            ListItem.Category.FOOD -> R.drawable.ic_food
            ListItem.Category.FILM_MUSIC -> R.drawable.ic_film_music
            ListItem.Category.PET -> R.drawable.ic_pet
            ListItem.Category.HOBBY -> R.drawable.ic_hobby
            ListItem.Category.STATIONERY -> R.drawable.ic_stationery
            ListItem.Category.HOME_HOUSEHOLD -> R.drawable.ic_home_household
            ListItem.Category.CLOTHING -> R.drawable.ic_clothing
            ListItem.Category.SPORT -> R.drawable.ic_sport
            ListItem.Category.OTHER -> R.drawable.ic_other
        }
        holder.binding.ivItemImg.setImageResource(image)
        holder.binding.tvItemName.text = currentItem.name
        holder.binding.tvItemBrand.text = currentItem.brand
        holder.binding.tvItemQuantity.text = currentItem.quantity
    }

    inner class ViewHolder(val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var item: ListItem? = null

        init {
            itemView.setOnClickListener {
                item?.let {item ->
                    itemClickListener?.onItemClick(item)
                }
            }

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