package m.z.ratp.test.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import m.z.ratp.test.common.viewmodel.BaseViewModel
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.data.repository.RATPRepository
import javax.inject.Inject

/**
 * Created by Mongi Zaidi on 12/03/2024.
 */
@HiltViewModel
class ToiletDetailsViewModel @Inject constructor(
    ratpRepository: RATPRepository
) : BaseViewModel(ratpRepository) {
    val toilet = MutableLiveData<Toilet>()
}