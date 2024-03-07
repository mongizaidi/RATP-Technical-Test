package m.z.ratp.test.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import m.z.ratp.test.databinding.FragmentToiletsListBinding

class ToiletsListFragment : Fragment() {

    private var _binding: FragmentToiletsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val toiletsListViewModel =
            ViewModelProvider(this)[ToiletsListViewModel::class.java]

        _binding = FragmentToiletsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textList
        toiletsListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}