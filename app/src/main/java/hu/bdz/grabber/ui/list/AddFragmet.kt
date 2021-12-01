package hu.bdz.grabber.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bdz.grabber.R
import hu.bdz.grabber.databinding.FragmentAddBinding
import hu.bdz.grabber.model.ListItem

class AddFragmet(val listViewModel: ListViewModel): DialogFragment()
{
    private lateinit var binding: FragmentAddBinding

    companion object {
        const val TAG = "AddItemDialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentAddBinding.inflate(inflater, container, false)
        dialog?.setTitle("Hozzáadás")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spnrItemCategory.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner,
            listOf("Ajándék", "Egészség", "Elektronika", "Élelmiszer", "Film/Zene", "Háziállat", "Hobbi", "Irodaszer", "Lakás/Háztartás", "Ruházat", "Sport", "Egyéb")
        )

        binding.btnAddItem.setOnClickListener {
            val selectedCategory = when (binding.spnrItemCategory.selectedItemPosition) {
                0 -> ListItem.Category.GIFT
                1 -> ListItem.Category.HEALTH
                2 -> ListItem.Category.ELECTRONICS
                3 -> ListItem.Category.FOOD
                4 -> ListItem.Category.FILM_MUSIC
                5 -> ListItem.Category.PET
                6 -> ListItem.Category.HOBBY
                7 -> ListItem.Category.STATIONERY
                8 -> ListItem.Category.HOME_HOUSEHOLD
                9 -> ListItem.Category.CLOTHING
                10 -> ListItem.Category.SPORT
                11 -> ListItem.Category.OTHER
                else -> ListItem.Category.OTHER
            }


            val newItem = ListItem(
                binding.etItemName.text.toString(),
                binding.etItemBrand.text.toString(),
                binding.etItemQuantity.text.toString(),
                selectedCategory,
                binding.etItemNote.text.toString(),
                false)
            listViewModel.insert(newItem)
            //listViewModel.deleteAll()
            dismiss()
        }

        binding.btnCancelAddItem.setOnClickListener {
            dismiss()
        }
    }
}