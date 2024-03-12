package m.z.ratp.test.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import m.z.ratp.test.common.viewmodel.BaseViewModel
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.data.repository.RATPRepository
import javax.inject.Inject

/**
 * Created by Mongi Zaidi on 06/03/2024.
 */
@HiltViewModel
class ToiletsViewModel @Inject constructor(
    ratpRepository: RATPRepository
) : BaseViewModel(ratpRepository) {
    private val toilets = MutableLiveData<List<Toilet>>()
    private val _filteredToilets = MutableLiveData<List<Toilet>>()
    val filteredToilets: LiveData<List<Toilet>> = _filteredToilets
    val showBottomProgressBar = MutableLiveData(false)
    val prmAccessOnly = MutableLiveData(false)

    fun fetchToilets(fromTop: Boolean = true) {
        if (isFetching) {
            return
        }
        isFetching = true
        showProgressBar.postValue(fromTop)
        showBottomProgressBar.postValue(!fromTop)
        viewModelScope.launch {
            val start = if (fromTop) 0 else toilets.value?.size ?: 0
            val rows = 10 // page size
            when (val response = ratpRepository.getToilets(start, rows)) {
                is NetworkResponse.Success -> {
                    val records = response.body.records.toMutableList()
                    if (!fromTop) {
                        toilets.value?.let { records.addAll(0, it) }
                    }
                    toilets.value = records
                    filterToilets()
                    isFetching = false
                    showProgressBar.postValue(false)
                    showBottomProgressBar.postValue(false)
                }

                is NetworkResponse.Error -> {
                    isFetching = false
                    showProgressBar.postValue(false)
                    showBottomProgressBar.postValue(false)
                    apiErrorHandler?.handleAPIError(response.body)
                }
            }
        }
    }

    fun filterToilets() {
        _filteredToilets.postValue(toilets.value?.filter { toilet ->
            prmAccessOnly.value != true || toilet.isPRMAccessible
        })
    }

    val onRecyclerScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy <= 0 || isFetching) {
                return
            }
            val layoutManager: LinearLayoutManager =
                recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
            if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                fetchToilets(false)
            }
        }
    }
}