package m.z.ratp.test.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import m.z.ratp.test.R
import m.z.ratp.test.common.ui.BaseLocationActivity
import m.z.ratp.test.common.ui.snackBarError
import m.z.ratp.test.data.api.APIErrorHandler
import m.z.ratp.test.data.model.APIError
import m.z.ratp.test.data.model.Toilet
import m.z.ratp.test.databinding.ActivityMainBinding
import m.z.ratp.test.ui.adapter.ToiletListener
import m.z.ratp.test.ui.viewmodel.ToiletsViewModel

/**
 * Created by Mongi Zaidi on 06/03/2024.
 */
@AndroidEntryPoint
class MainActivity : BaseLocationActivity(), ToiletListener, APIErrorHandler {

    private lateinit var binding: ActivityMainBinding
    val viewModel: ToiletsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list, R.id.navigation_map
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.apiErrorHandler = this
        viewModel.fetchToilets()
        binding.swPrm.setOnCheckedChangeListener { _, isChecked ->
            viewModel.prmAccessOnly.value = isChecked
            viewModel.filterToilets()
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentUserLocation {}
    }

    override fun onToiletClicked(toilet: Toilet) {
        val intent = Intent(this, ToiletDetailsActivity::class.java)
        intent.putExtra("toilet", toilet)
        startActivity(intent)
    }

    override fun handleAPIError(apiError: APIError?) {
        snackBarError("${apiError?.message}\n${apiError?.errorCode}")
    }
}