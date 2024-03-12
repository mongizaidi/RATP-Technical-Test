package m.z.ratp.test.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
data class ServerResponse(
    @SerializedName("nhits")
    val totalCount: Int,
    val parameters: RequestParameters,
    val records: List<Toilet>
)

data class RequestParameters(
    val dataset: String,
    val rows: Int,
    val start: Int,
    val format: String,
    val timezone: String
)