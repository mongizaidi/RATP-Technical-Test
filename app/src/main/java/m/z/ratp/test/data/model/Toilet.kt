package m.z.ratp.test.data.model

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import m.z.ratp.test.R
import m.z.ratp.test.common.adapter.ListAdapterItem
import m.z.ratp.test.common.ui.BaseLocationActivity
import m.z.ratp.test.common.ui.getActivity
import java.io.Serializable

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
data class Toilet(
    @SerializedName("datasetid")
    val datasetId: String?,
    @SerializedName("recordid")
    val recordId: String?,
    val fields: Fields?
) : ListAdapterItem, Serializable {
    val isPRMAccessible: Boolean
        get() = fields?.reducedMobilityAccess == "Oui"
    val fullAddress: String
        get() {
            val streetAddress = fields?.address ?: ""
            val city = fields?.district ?: ""
            return "$streetAddress $city".trim()
        }
    val latLng: LatLng?
        get() = if (fields?.geoPoint2d?.size == 2) LatLng(
            fields.geoPoint2d.first(),
            fields.geoPoint2d.last()
        ) else null

    fun distanceToUserLocation(context: Context): String? {
        val userLocation = (context.getActivity() as? BaseLocationActivity)?.lastUserLocation ?: return null
        val latLng = latLng ?: return null
        val myLocation = Location("")
        myLocation.latitude = latLng.latitude
        myLocation.longitude = latLng.longitude
        val distance = userLocation.distanceTo(myLocation).toInt()
        return context.getString(
            if (distance < 1000) R.string.distance_meters else R.string.distance_kms,
            distance
        )
    }
}

data class Fields(
    @SerializedName("horaire")
    val openingHours: String?,
    @SerializedName("acces_pmr")
    val reducedMobilityAccess: String?,
    @SerializedName("arrondissement")
    val district: String?,
    @SerializedName("adresse")
    val address: String?,
    @SerializedName("geo_point_2d")
    val geoPoint2d: List<Double>?
) : Serializable