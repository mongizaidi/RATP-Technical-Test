package m.z.ratp.test.data.api

import com.haroldadmin.cnradapter.NetworkResponse
import m.z.ratp.test.data.model.APIError
import m.z.ratp.test.data.model.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
interface RATPAPIClient {

    companion object {
        const val CLIENT_TIMEOUT: Long = 30
    }

    @GET("search?dataset=sanisettesparis2011")
    suspend fun getToilets(
        @Query("start") start: Int,
        @Query("rows") rows: Int
    ): NetworkResponse<ServerResponse, APIError>
}