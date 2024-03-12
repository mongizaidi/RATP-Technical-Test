package m.z.ratp.test.ui.adapter

import m.z.ratp.test.R
import m.z.ratp.test.common.adapter.BaseAdapter
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.databinding.ItemToiletBinding

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
class ToiletAdapter(
    list: List<Toilet>,
    private val toiletListener: ToiletListener
) : BaseAdapter<ItemToiletBinding, Toilet>(list) {

    override val layoutId: Int = R.layout.item_toilet

    override fun bind(binding: ItemToiletBinding, item: Toilet) {
        binding.apply {
            toilet = item
            listener = toiletListener
            isLast = data.last() == item
            executePendingBindings()
        }
    }
}

interface ToiletListener {
    fun onToiletClicked(toilet: Toilet)
}