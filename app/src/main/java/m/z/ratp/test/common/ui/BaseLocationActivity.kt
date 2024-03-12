package m.z.ratp.test.common.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Date

/**
 * Created by Mongi Zaidi on 12/03/2024.
 */
open class BaseLocationActivity : AppCompatActivity() {

    private var pendingCompletion: ((Boolean) -> Unit)? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastPermissionRequestTime: Long = 0
    var lastUserLocation: Location? = null

    fun getCurrentUserLocation(completion: (Location?) -> Unit) {
        checkLocationPermission { granted ->
            if (granted) {
                if (fusedLocationClient == null) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                }
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    lastUserLocation = location
                    completion.invoke(location)
                }?.addOnFailureListener {
                    completion(null)
                    lastUserLocation = null
                }
            } else {
                completion(null)
                lastUserLocation = null
            }
        }
    }

    private fun checkLocationPermission(completion: (Boolean) -> Unit) {
        checkPermissions(completion)
    }

    private fun checkPermissions(
        completion: (Boolean) -> Unit
    ) {
        pendingCompletion = completion
        val requiredPermissions = ArrayList<String>()
        if (!isPermissionGranted(ACCESS_COARSE_LOCATION)) {
            requiredPermissions.add(ACCESS_COARSE_LOCATION)
            if (!isPermissionGranted(ACCESS_FINE_LOCATION)) {
                requiredPermissions.add(ACCESS_FINE_LOCATION)
            }
        }
        if (requiredPermissions.isEmpty()) {
            completion.invoke(true)
        } else {
            if (Date().time - lastPermissionRequestTime < 1000) {
                completion.invoke(false)
                return
            }
            lastPermissionRequestTime = Date().time
            permissionRequest.launch(requiredPermissions.toTypedArray())
        }
    }


    private fun onPermissionsGranted() {
        pendingCompletion?.invoke(true)
    }

    private fun onPermissionsDenied() {
        pendingCompletion?.invoke(false)
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var permissionsGranted =
                isPermissionGranted(ACCESS_COARSE_LOCATION) && isPermissionGranted(
                    ACCESS_FINE_LOCATION
                )
            if (permissionsGranted && Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                permissionsGranted =
                    isPermissionGranted(BLUETOOTH_SCAN) && isPermissionGranted(BLUETOOTH_CONNECT)
            }
            if (permissionsGranted) {
                onPermissionsGranted()
            } else {
                onPermissionsDenied()
            }
        }

}