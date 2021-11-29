package hu.bdz.grabber.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import hu.bdz.grabber.R
import hu.bdz.grabber.adapter.ListRecyclerAdapter
import hu.bdz.grabber.databinding.FragmentListBinding

class ListFragment : Fragment() {

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
        binding.root.findViewById<RecyclerView>(R.id.list_recycler_view).adapter = adapter
    }
}