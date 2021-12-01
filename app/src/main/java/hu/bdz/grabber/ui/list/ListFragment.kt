package hu.bdz.grabber.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hu.bdz.grabber.R
import hu.bdz.grabber.adapter.ListRecyclerAdapter
import hu.bdz.grabber.databinding.FragmentListBinding
import hu.bdz.grabber.model.ListItem

class ListFragment : Fragment(), ListRecyclerAdapter.ItemClickListener {

    private lateinit var listViewModel: ListViewModel
    private var _binding: FragmentListBinding? = null

    private lateinit var adapter: ListRecyclerAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.allItems.observe(viewLifecycleOwner, { items ->
            adapter.submitList(items)
        })

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        binding.btnAdd.setOnClickListener {
            AddFragmet(listViewModel).show(childFragmentManager, AddFragmet.TAG)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView()
    {
        adapter = ListRecyclerAdapter()
        adapter.itemClickListener = this
        binding.listRecyclerView.adapter = adapter
    }

    override fun onItemClick(item: ListItem) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int, view: View, _item: ListItem): Boolean {
        val popup = PopupMenu(activity?.applicationContext, view)
        Log.d("TAG_LC", "Long Click")
        popup.inflate(R.menu.menu_item)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    listViewModel.delete(_item)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }
}