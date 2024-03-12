package m.z.ratp.test.common.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import m.z.ratp.test.data.api.APIErrorHandler
import m.z.ratp.test.data.repository.RATPRepository
import javax.inject.Inject

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
@HiltViewModel
open class BaseViewModel @Inject constructor(
    val ratpRepository: RATPRepository,
) :
    ViewModel() {
    val showProgressBar = MutableLiveData(false)
    protected var isFetching = false
    var apiErrorHandler: APIErrorHandler? = null
}