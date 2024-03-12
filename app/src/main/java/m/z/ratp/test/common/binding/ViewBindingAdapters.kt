package m.z.ratp.test.common.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import m.z.ratp.test.common.adapter.BaseAdapter
import m.z.ratp.test.common.adapter.ListAdapterItem

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */

@BindingAdapter("adapter")
fun adapter(
    recyclerView: RecyclerView,
    adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?,
) {
    adapter?.let {
        recyclerView.adapter = it
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun data(recyclerView: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.updateData(list ?: listOf())
}

@BindingAdapter("scrollListener")
fun setScrollListener(view: RecyclerView, scrollListener: RecyclerView.OnScrollListener?) {
    view.clearOnScrollListeners()
    scrollListener?.let { view.addOnScrollListener(scrollListener) }
}

@BindingAdapter("layoutMarginBottom")
fun setLayoutMarginBottom(view: View, dimen: Float) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = dimen.toInt()
    view.layoutParams = layoutParams
}