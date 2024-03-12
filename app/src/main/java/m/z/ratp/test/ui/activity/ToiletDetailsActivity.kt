package m.z.ratp.test.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import m.z.ratp.test.common.ui.BaseLocationActivity
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.databinding.ActivityToiletDetailsBinding
import m.z.ratp.test.ui.viewmodel.ToiletDetailsViewModel

/**
 * Created by Mongi Zaidi on 12/03/2024.
 */
@AndroidEntryPoint
class ToiletDetailsActivity : BaseLocationActivity() {

    private lateinit var binding: ActivityToiletDetailsBinding
    val viewModel: ToiletDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToiletDetailsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel.toilet.value = intent.extras?.get("toilet") as Toilet?
        getCurrentUserLocation {
            // force refresh to add distance text
            viewModel.toilet.postValue(viewModel.toilet.value)
        }
    }
}