package m.z.ratp.test.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import m.z.ratp.test.databinding.FragmentToiletsListBinding
import m.z.ratp.test.ui.activity.MainActivity
import m.z.ratp.test.ui.adapter.ToiletAdapter

/**
 * Created by Mongi Zaidi on 06/03/2024.
 */
@AndroidEntryPoint
class ToiletsListFragment : Fragment() {

    private var _binding: FragmentToiletsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToiletsListBinding.inflate(inflater, container, false)
        binding.viewModel = (activity as MainActivity).viewModel
        binding.lifecycleOwner = this
        binding.adapter = ToiletAdapter(listOf(), activity as MainActivity)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.filterToilets()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}