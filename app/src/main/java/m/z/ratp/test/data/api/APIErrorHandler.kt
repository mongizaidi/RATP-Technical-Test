package m.z.ratp.test.data.api

import m.z.ratp.test.data.model.APIError


/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
interface APIErrorHandler {
    fun handleAPIError(apiError: APIError?)
}