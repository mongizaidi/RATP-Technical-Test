package m.z.ratp.test.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import m.z.ratp.test.R
import m.z.ratp.test.common.ui.BaseLocationActivity
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.databinding.FragmentToiletsMapBinding
import m.z.ratp.test.ui.activity.MainActivity
import m.z.ratp.test.ui.adapter.ToiletListener

/**
 * Created by Mongi Zaidi on 06/03/2024.
 */
@AndroidEntryPoint
class ToiletsMapFragment : Fragment() {

    private var _binding: FragmentToiletsMapBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        addMarkers()
        googleMap.isMyLocationEnabled =
            (activity as? BaseLocationActivity)?.lastUserLocation != null
        googleMap.uiSettings.isMyLocationButtonEnabled =
            (activity as? BaseLocationActivity)?.lastUserLocation != null
        googleMap.setOnInfoWindowClickListener {
            (activity as? ToiletListener)?.onToiletClicked(it.tag as Toilet)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToiletsMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        (activity as? MainActivity)?.viewModel?.filteredToilets?.observe(viewLifecycleOwner) {
            addMarkers()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addMarkers() {
        googleMap?.clear()
        val builder = LatLngBounds.builder()
        (activity as? MainActivity)?.viewModel?.filteredToilets?.value?.forEach { toilet ->
            toilet.latLng?.let { latLng ->
                val marker = googleMap?.addMarker(MarkerOptions().position(latLng).title(toilet.fullAddress))
                marker?.tag = toilet
                builder.include(latLng)
            }
        }
        val bounds = builder.build()
        try {
            googleMap?.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}