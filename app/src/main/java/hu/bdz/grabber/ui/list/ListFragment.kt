package hu.bdz.grabber.ui.list

import android.os.Bundle
import android.util.Log
import android.view.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        item.bought = item.bought != true
        listViewModel.update(item)
        adapter.notifyDataSetChanged()
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
                R.id.edit -> {
                    EditFragment(listViewModel, _item).show(childFragmentManager, EditFragment.TAG)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popup.show()
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteBought) {
            listViewModel.deleteBought()
        }
        return super.onOptionsItemSelected(item)
    }
}