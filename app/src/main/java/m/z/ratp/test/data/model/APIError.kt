package m.z.ratp.test.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
data class APIError(
    @SerializedName("error_code")
    val errorCode: String?,
    val message: String?,
)