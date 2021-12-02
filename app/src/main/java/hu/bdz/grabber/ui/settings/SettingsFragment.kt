package hu.bdz.grabber.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import hu.bdz.grabber.MainActivity
import hu.bdz.grabber.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        const val KEY_IS_LOCATION_SERVICE_RUNNING = "is_location_service_running"
    }

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val sp = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext)

        binding.swLocation.isChecked = sp.getBoolean(KEY_IS_LOCATION_SERVICE_RUNNING, false)

        binding.swLocation.setOnCheckedChangeListener { buttonView, isChecked ->
            val _sp = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext)
            if (isChecked) {
                _sp.edit().putBoolean(KEY_IS_LOCATION_SERVICE_RUNNING, true).apply()
            }
            else {
                _sp.edit().putBoolean(KEY_IS_LOCATION_SERVICE_RUNNING, false).apply()
            }
            (activity as MainActivity).resetLocationService()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}