package m.z.ratp.test.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import m.z.ratp.test.data.api.RATPAPIClient
import m.z.ratp.test.data.model.APIError
import m.z.ratp.test.data.model.ServerResponse
import javax.inject.Inject

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
class RATPRepository @Inject constructor(private val ratpapiClient: RATPAPIClient) {

    suspend fun getToilets(start: Int = 0, rows: Int = 1000): NetworkResponse<ServerResponse, APIError> {
        return ratpapiClient.getToilets(start, rows)
    }

}