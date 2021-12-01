package hu.bdz.grabber.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bdz.grabber.R
import hu.bdz.grabber.databinding.FragmentAddEditBinding
import hu.bdz.grabber.model.ListItem

class EditFragment(val listViewModel: ListViewModel, var itemToUpdate: ListItem): DialogFragment() {
    private lateinit var binding: FragmentAddEditBinding

    companion object {
        const val TAG = "EditItemDialog"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentAddEditBinding.inflate(inflater, container, false)
        dialog?.setTitle("Szerkesztés")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etItemName.setText(itemToUpdate.name)
        binding.etItemBrand.setText(itemToUpdate.brand)
        binding.etItemQuantity.setText(itemToUpdate.quantity)

        binding.spnrItemCategory.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner,
            listOf("Ajándék", "Egészség", "Elektronika", "Élelmiszer", "Film/Zene", "Háziállat", "Hobbi", "Irodaszer", "Lakás/Háztartás", "Ruházat", "Sport", "Egyéb")
        )
        val categorySelection = when (itemToUpdate.category) {
            ListItem.Category.GIFT -> 0
            ListItem.Category.HEALTH -> 1
            ListItem.Category.ELECTRONICS -> 2
            ListItem.Category.FOOD -> 3
            ListItem.Category.FILM_MUSIC -> 4
            ListItem.Category.PET -> 5
            ListItem.Category.HOBBY -> 6
            ListItem.Category.STATIONERY -> 7
            ListItem.Category.HOME_HOUSEHOLD -> 8
            ListItem.Category.CLOTHING -> 9
            ListItem.Category.SPORT -> 10
            ListItem.Category.OTHER -> 11
        }
        binding.spnrItemCategory.setSelection(categorySelection)
        binding.etItemNote.setText(itemToUpdate.note)


        binding.btnOk.setOnClickListener {
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

            val updatedItem = ListItem(
                itemToUpdate.id,
                binding.etItemName.text.toString(),
                binding.etItemBrand.text.toString(),
                binding.etItemQuantity.text.toString(),
                selectedCategory,
                binding.etItemNote.text.toString(),
                itemToUpdate.bought)
            listViewModel.update(updatedItem)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}